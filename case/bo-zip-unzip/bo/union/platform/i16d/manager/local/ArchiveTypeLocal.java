/**
 *  ____                          _   _       _    __
 * | __ )  __ _ _ __   ___ ___   | | | |_ __ (_) _/_/ _ __
 * |  _ \ / _` | '_ \ / __/ _ \  | | | | '_ \| |/ _ \| '_ \
 * | |_) | (_| | | | | (_| (_) | | |_| | | | | | (_) | | | |
 * |____/ \__,_|_| |_|\___\___/   \___/|_| |_|_|\___/|_| |_|
 *  ____                            _           ____
 * |  _ \ _ __ ___  _   _  ___  ___| |_ ___    / ___|___  _ __ ___
 * | |_) | '__/ _ \| | | |/ _ \/ __| __/ _ \  | |   / _ \| '__/ _ \
 * |  __/| | | (_) | |_| |  __/ (__| || (_) | | |__| (_) | | |  __/
 * |_|   |_|  \___/ \__, |\___|\___|\__\___/   \____\___/|_|  \___|
 *                  |___/
 *
 * Copyright © 2017 - http://union.dev/licence.txt#yracnet
 */
package bo.union.platform.i16d.manager.local;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import bo.union.persist.qfilter.QFilter;
import bo.union.lang.ServiceException;
import bo.union.lang.ValidationException;
import bo.union.comp.filter.MapFilter;
import bo.union.platform.i16d.manager.entity.I16dArchiveType;
import javax.persistence.Query;

@Stateless
@LocalBean
public class ArchiveTypeLocal {

	@PersistenceContext(unitName = "i16d-manager")
	private EntityManager em;

	public List<I16dArchiveType> filterArchiveType(MapFilter filter) throws ServiceException {
		return QFilter.filter(em, I16dArchiveType.class, filter);
	}

	public I16dArchiveType findArchiveType(Long id) {
		return em.find(I16dArchiveType.class, id);
	}

	public I16dArchiveType findArchiveTypeOfConfig(Long archiveConfigId) {
		List<I16dArchiveType> list = em.createQuery("SELECT o FROM I16dArchiveType o WHERE o.i16dArchiveConfig.archiveConfigId = :archiveConfigId")
				.setParameter("archiveConfigId", archiveConfigId).getResultList();
		return list.isEmpty() ? null : list.get(0);
	}

	public I16dArchiveType findArchiveType(String code) {
		List<I16dArchiveType> list = em.createQuery("SELECT o FROM I16dArchiveType o WHERE o.code = :code").setParameter("code", code).getResultList();
		return list.isEmpty() ? null : list.get(0);
	}

	public I16dArchiveType createArchiveType(I16dArchiveType value) throws ServiceException {
		ValidationException validate = new ValidationException("Registro ArchiveType");
		validateCreateArchiveType(value, validate);
		validate(value, validate);
		em.persist(value);
		return value;
	}

	public I16dArchiveType updateArchiveType(I16dArchiveType entity) throws ServiceException {
		ValidationException validate = new ValidationException("Actualizar ArchiveType");
		validateUpdateArchiveType(entity, validate);
		validate(entity, validate);
		em.merge(entity);
		return entity;
	}

	public I16dArchiveType removeArchiveType(I16dArchiveType entity) throws ServiceException {
		ValidationException validate = new ValidationException("Eliminar ArchiveType");
		validateRemoveArchiveType(entity, validate);
		em.remove(entity);
		return entity;
	}

	public void validateCreateArchiveType(I16dArchiveType entity, ValidationException validate) throws ServiceException {
		validate.isNull(entity, "Archive Type");
		validate.throwException();
		validate.isNotNull(entity.getArchiveTypeId(), "archiveTypeId");
		validate.throwException();
		validate.isNull(entity.getI16dArchiveConfig(), "archiveConfig");
		validate.throwException();
		// Long cant = validateUniqueCode(entity.getCode());
		int cant = validateUniqueCode(entity.getCode()).size();
		validate.addWhen(cant > 0, "Ya existe el code [" + entity.getCode() + "] ingrese otro code");
		validate.throwException();
	}

	public void validateUpdateArchiveType(I16dArchiveType entity, ValidationException validate) throws ServiceException {
		validate.isNull(entity, "Archive Type");
		validate.throwException();
		validate.isNull(entity.getI16dArchiveConfig(), "archiveConfig");
		validate.throwException();
		List<I16dArchiveType> list = validateUniqueCode(entity.getCode());
		if (list.size() > 0) {
			validate.addWhen(!list.get(0).getArchiveTypeId().equals(entity.getArchiveTypeId()),
					"Ya existe el code [" + entity.getCode() + "] ingrese otro code");
			validate.throwException();
		}
	}

	public void validateRemoveArchiveType(I16dArchiveType value, ValidationException validate) throws ServiceException {
		validate.isNull(value, "Archive Type");
		validate.throwException();
		validate.isNullOrEmpty(value.getArchiveTypeId(), "archiveTypeId");
		validate.throwException();
	}

	public void validate(I16dArchiveType entity, ValidationException validate) throws ServiceException {
		validate.isNull(entity, "Archive Type");
		validate.throwException();
		validate.isNull(entity.getCode(), "Code");
		validate.isNullOrNotRegExp(entity.getCode(), HelpLocal.REGEX_NAME, "Code");
		validate.throwException();
		validate.isNullOrLength(entity.getCode(), 3, 30, "Code");
		validate.throwException();
		validate.isNull(entity.getOrigin(), "Origin");
		validate.throwException();
		validate.isNullOrLength(entity.getOrigin(), 2, 30, "Origen");
		validate.throwException();
		validate.isNull(entity.getSender(), "Sender");
		validate.throwException();
		validate.isNullOrLength(entity.getSender(), 1, 30, "Sender");
		validate.throwException();
		validate.isNull(entity.getFormat(), "Format");
		validate.throwException();
		validate.isNullOrLength(entity.getFormat(), 1, 10, "Format");
		validate.throwException();
		validate.isNull(entity.getEmailList(), "Email");
		validate.throwException();
		validate.addWhen(entity.getEmailList().isEmpty(), "Email");
		validate.throwException();
		validateEmail(entity.getEmailList(), validate);
		validate.isNull(entity.getBehavior(), "Behavior");
		validate.throwException();
		validate.isNullOrLength(entity.getBehavior(), 1, 200, "behavior");
		validate.throwException();
		validate.isNull(entity.getDescription(), "Description");
		validate.isNullOrNotRegExp(entity.getDescription(), HelpLocal.REGEX_DESCRIPTION, "Descripcion");
		validate.throwException();
		validate.isNullOrLength(entity.getDescription(), 10, 100, "Descripcion");
		validate.throwException();
	}

	public void validateEmail(List<String> list, ValidationException validate) throws ValidationException {
		for (String mail : list) {
			validate.isNullOrLength(mail, 5, 50, "Email");
			validate.throwException();
		}
	}

	// public Long validateUniqueCode(String code) {
	// Query q = em.createQuery("SELECT COUNT(o) FROM I16dArchiveType o WHERE o.code =
	// :code").setParameter("code", code);
	// return (Long) q.getSingleResult();
	// }
	public List<I16dArchiveType> validateUniqueCode(String code) {
		Query q = em.createQuery("SELECT o FROM I16dArchiveType o WHERE o.code = :code").setParameter("code", code);
		return q.getResultList();
	}
}

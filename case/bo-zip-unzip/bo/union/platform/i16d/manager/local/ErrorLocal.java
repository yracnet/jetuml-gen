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
import bo.union.platform.i16d.manager.entity.I16dError;

@Stateless
@LocalBean
public class ErrorLocal {

	@PersistenceContext(unitName = "i16d-manager")
	private EntityManager em;

	public List<I16dError> filterError(MapFilter filter) throws ServiceException {
		return QFilter.filter(em, I16dError.class, filter);
	}

	public List<I16dError> listError(Long idValueArchive) throws ServiceException {
		List<I16dError> list = em.createQuery("SELECT o FROM I16dError o WHERE o.i16dValueArchive.valueArchiveId = :idValueArchive ORDER BY o.attempt DESC")
				.setParameter("idValueArchive", idValueArchive).getResultList();
		return list.isEmpty() ? null : list;
	}

	public I16dError createError(I16dError value) throws ServiceException {
		ValidationException validate = new ValidationException("Registro Error");
		validateCreateError(value, validate);
		em.persist(value);
		return value;
	}

	public I16dError updateError(I16dError entity) throws ServiceException {
		ValidationException validate = new ValidationException("Actualizar Error");
		validateUpdateError(entity, validate);
		em.merge(entity);
		return entity;
	}

	public I16dError removeError(I16dError entity) throws ServiceException {
		ValidationException validate = new ValidationException("Eliminar Error");
		validateRemoveError(entity, validate);
		em.remove(entity);
		return entity;
	}

	public void validateCreateError(I16dError entity, ValidationException validate) throws ServiceException {
		validate.isNull(entity, "Error");
		validate.throwException();
		validate.isNotNull(entity.getErrorId(), "errorId");
		validate(entity, validate);
		validate.throwException();
	}

	public void validateUpdateError(I16dError entity, ValidationException validate) throws ServiceException {
		validate.isNull(entity, "Error");
		validate.throwException();
		validate.isNullOrEmpty(entity.getErrorId(), "errorId");
		validate(entity, validate);
		validate.throwException();
	}

	public void validateRemoveError(I16dError value, ValidationException validate) throws ServiceException {
		validate.isNull(value, "Error");
		validate.throwException();
		validate.isNullOrEmpty(value.getErrorId(), "idError");
		validate.throwException();
	}

	public void validate(I16dError entity, ValidationException validate) throws ValidationException {
		validate.isNullOrEmpty(entity.getAtLine(), "At Line");
		validate.isNullOrEmpty(entity.getAttempt(), "Attemp");
		validate.isNullOrEmpty(entity.getDescription(), "Description");
		validate.isNullOrEmpty(entity.getNomArchive(), "Nom Archive");
		validate.isNullOrEmpty(entity.getNameColumn(), "Name Column");
	}
}

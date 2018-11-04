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
import bo.union.platform.i16d.manager.Config;
import bo.union.platform.i16d.manager.entity.I16dPrivilege;
import javax.persistence.Query;

@Stateless
@LocalBean
public class PrivilegeLocal {

	@PersistenceContext(unitName = Config.UNIT_NAME)
	private EntityManager em;

	public List<I16dPrivilege> filterPrivilege(MapFilter filter) throws ServiceException {
		return QFilter.filter(em, I16dPrivilege.class, filter);
	}

	public I16dPrivilege findPrivilege(Long privilegeId) {
		return privilegeId == null ? null : em.find(I16dPrivilege.class, privilegeId);
	}

	public I16dPrivilege createPrivilege(I16dPrivilege value) throws ServiceException {
		ValidationException validate = new ValidationException("Registro Privilege");
		validateCreatePrivilege(value, validate);
		em.persist(value);
		return value;
	}

	public I16dPrivilege updatePrivilege(I16dPrivilege entity) throws ServiceException {
		ValidationException validate = new ValidationException("Actualizar Privilege");
		validateUpdatePrivilege(entity, validate);
		em.merge(entity);
		return entity;
	}

	public I16dPrivilege removePrivilege(I16dPrivilege entity) throws ServiceException {
		ValidationException validate = new ValidationException("Eliminar Privilege");
		validateRemovePrivilege(entity, validate);
		em.remove(entity);
		return entity;
	}

	public void validateCreatePrivilege(I16dPrivilege entity, ValidationException validate) throws ServiceException {
		validate.isNull(entity, "Privilege");
		validate.throwException();
		validate.isNotNull(entity.getPrivilegeId(), "privilegeId");
		validateUniquePrivilege(entity, validate);
		validate.throwException();
		validate(entity, validate);
		validate.throwException();
	}

	public void validateUpdatePrivilege(I16dPrivilege entity, ValidationException validate) throws ServiceException {
		validate.isNull(entity, "Privilege");
		validate.throwException();
		validate.isNullOrEmpty(entity.getPrivilegeId(), "privilegeId");
		validate(entity, validate);
		validate.throwException();
	}

	public void validateRemovePrivilege(I16dPrivilege value, ValidationException validate) throws ServiceException {
		validate.isNull(value, "Privilege");
		validate.throwException();
		validate.isNullOrEmpty(value.getPrivilegeId(), "privilegeId");
		validate.throwException();
	}

	public void validate(I16dPrivilege entity, ValidationException validate) throws ValidationException {
	}

	public void validateUniquePrivilege(I16dPrivilege entity, ValidationException validate) {
		Query q = em.createQuery("SELECT o FROM I16dPrivilege o WHERE o.i16dBean.beanId = :beanId AND o.versionId = :versionId")
				.setParameter("beanId", entity.getI16dBean().getBeanId()).setParameter("versionId", entity.getVersionId());
		List<I16dPrivilege> list = q.getResultList();
		validate.addWhen(!list.isEmpty(), "Ya tiene permisos el Bean [" + entity.getI16dBean().getName() + "] en Modulo ["
				+ entity.getI16dDeployment().getName() + entity.getVersionId() + "]");
	}
}

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
import bo.union.platform.i16d.manager.entity.I16dDeployment;
import bo.union.platform.i16d.manager.entity.I16dDeploymentExt;
import bo.union.platform.i16d.manager.entity.I16dVersion;

@Stateless
@LocalBean
public class DeploymentLocal {

	@PersistenceContext(unitName = Config.UNIT_NAME)
	private EntityManager em;

	public List<I16dDeployment> filterDeployment(MapFilter filter) throws ServiceException {
		return QFilter.filter(em, I16dDeployment.class, filter);
	}

	public List<I16dVersion> filterVersion(String deploymentId) throws ServiceException {
		List<I16dVersion> list = em.createQuery("SELECT o FROM I16dVersion o WHERE o.deploymentId = :deploymentId")
				.setParameter("deploymentId", deploymentId).getResultList();
		if (list.isEmpty()) {
			I16dVersion entity = new I16dVersion();
			entity.setVersion("NONE");
			list.add(entity);
		}
		return list;
	}

	public I16dDeployment findDeployment(String id) throws ServiceException {
		return em.find(I16dDeployment.class, id);
	}

	public I16dDeployment updateDeployment(I16dDeployment entity) throws ServiceException {
		ValidationException validate = new ValidationException("Actualizar Deployment");
		validateUpdateDeployment(entity, validate);
		em.merge(entity);
		return entity;
	}

	public void validateUpdateDeployment(I16dDeployment entity, ValidationException validate) throws ServiceException {
		validate.isNull(entity, "Deployment");
		validate.throwException();
		validate.isNullOrEmpty(entity.getDeploymentId(), "deploymentId");
		validate.isNullOrEmpty(entity.getI16dDeploymentExt(), "deploymentExt");
		validate.throwException();
		validate(entity.getI16dDeploymentExt(), validate);
		validate.throwException();
	}

	public void validate(I16dDeploymentExt entity, ValidationException validate) throws ValidationException {
		validate.isNullOrEmpty(entity.isActive(), "Active");
		validate.isNullOrEmpty(entity.isLogger(), "Logger");
		validate.isNullOrLength(entity.getVersionId(), 3, 30, "Version");
	}
}
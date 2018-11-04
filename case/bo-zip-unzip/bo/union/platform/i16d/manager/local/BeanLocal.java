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
import bo.union.platform.i16d.manager.entity.I16dBean;
import bo.union.platform.i16d.manager.entity.I16dBeanMethod;
import bo.union.platform.i16d.manager.entity.I16dBeanMethodExt;
import bo.union.platform.i16d.manager.entity.I16dDeployment;
import bo.union.platform.i16d.manager.entity.I16dVersion;
import java.util.ArrayList;

@Stateless
@LocalBean
public class BeanLocal {

	@PersistenceContext(unitName = Config.UNIT_NAME)
	private EntityManager em;

	public List<I16dVersion> filterVersion(MapFilter filter) throws ServiceException {
		return QFilter.filter(em, I16dVersion.class, filter);
	}

	public List<I16dBean> filterBean(MapFilter filter) throws ServiceException {
		return QFilter.filter(em, I16dBean.class, filter);
	}

	public List<I16dBeanMethod> filterBeanMethod(MapFilter filter) throws ServiceException {
		return QFilter.filter(em, I16dBeanMethod.class, filter);
	}

	public List<I16dDeployment> getModule() {
		List<I16dDeployment> listDeployment = em.createQuery("SELECT o FROM I16dDeployment o WHERE o.type = 'MODULE'").getResultList();
		return listDeployment.isEmpty() ? null : listDeployment;
	}

	public List<I16dBean> listBean(String deploymentId, String deploymentVersion) throws ServiceException {
		List<I16dVersion> listVersion = em
				.createQuery("SELECT o FROM I16dVersion o WHERE o.deploymentId = :deploymentId AND o.version = :deploymentVersion")
				.setParameter("deploymentId", deploymentId).setParameter("deploymentVersion", deploymentVersion).getResultList();
		List<I16dBean> listBean = new ArrayList<>();
		if (!listVersion.isEmpty()) {
			listBean = em.createQuery("SELECT o FROM I16dBean o WHERE o.versionId = :versionId").setParameter("versionId", listVersion.get(0).getVersionId())
					.getResultList();
		}
		return listBean.isEmpty() ? null : listBean;
	}

	public I16dVersion findVersion(String versionId) throws ServiceException {
		return em.find(I16dVersion.class, versionId);
	}

	public I16dBean findBean(String beanId) throws ServiceException {
		return em.find(I16dBean.class, beanId);
	}

	public I16dBeanMethod findBeanMethod(String id) throws ServiceException {
		return em.find(I16dBeanMethod.class, id);
	}

	public I16dBeanMethod updateBeanMethod(I16dBeanMethod entity) throws ServiceException {
		ValidationException validate = new ValidationException("Actualizar BeanMethod");
		validateUpdateBeanMethod(entity, validate);
		em.merge(entity);
		return entity;
	}

	public void validateUpdateBeanMethod(I16dBeanMethod entity, ValidationException validate) throws ServiceException {
		validate.isNull(entity, "Bean Method");
		validate.throwException();
		validate.isNullOrEmpty(entity.getBeanMethodId(), "beanMethodId");
		validate.isNullOrEmpty(entity.getI16dBeanMethodExt(), "beanMethodExt");
		validate.throwException();
		validate(entity.getI16dBeanMethodExt(), validate);
		validate.throwException();
	}

	public void validate(I16dBeanMethodExt entity, ValidationException validate) throws ValidationException {
		validate.isNullOrEmpty(entity.isActive(), "Active");
	}
}
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
package bo.union.platform.i16d.provider;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import bo.union.lang.ServiceException;
import bo.union.platform.i16d.manager.BeanServ;
import bo.union.platform.i16d.manager.data.Bean;
import bo.union.platform.i16d.manager.data.BeanMethod;
import bo.union.platform.i16d.manager.data.Deployment;
import bo.union.platform.i16d.manager.data.Version;
import bo.union.platform.i16d.manager.filter.BeanFtr;
import bo.union.platform.i16d.manager.local.BeanLocal;
import bo.union.platform.i16d.manager.entity.I16dBean;
import bo.union.platform.i16d.manager.entity.I16dBeanMethod;
import bo.union.platform.i16d.manager.entity.I16dDeployment;
import bo.union.platform.i16d.manager.entity.I16dVersion;
import bo.union.platform.i16d.manager.filter.BeanMethodFtr;
import bo.union.platform.i16d.manager.filter.VersionFtr;
import bo.union.platform.i16d.manager.mapper.BeanMapper;
import bo.union.platform.i16d.manager.mapper.BeanMethodMapper;
import bo.union.platform.i16d.manager.mapper.DeploymentMapper;
import bo.union.platform.i16d.manager.mapper.VersionMapper;

@Stateless
@Local(BeanServ.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class BeanImpl implements BeanServ {

	@EJB
	private BeanLocal local;

	@Override
	public List<Version> filterVersion(VersionFtr filter) throws ServiceException {
		try {
			List<I16dVersion> result = local.filterVersion(filter);
			return VersionMapper.mapperToVersionList(result);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error no controlado", e);
		}
	}

	@Override
	public List<Bean> filterBean(BeanFtr filter) throws ServiceException {
		try {
			List<I16dBean> result = local.filterBean(filter);
			return BeanMapper.mapperToBeanList(result);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error no controlado", e);
		}
	}

	@Override
	public List<BeanMethod> filterBeanMethod(BeanMethodFtr filter) throws ServiceException {
		try {
			List<I16dBeanMethod> result = local.filterBeanMethod(filter);
			return BeanMethodMapper.mapperToBeanMethodList(result);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error no controlado", e);
		}
	}

	@Override
	public BeanMethod updateBeanMethod(BeanMethod value) throws ServiceException {
		try {
			I16dBeanMethod entity = BeanMethodMapper.mapperToI16dBeanMethod(value);
			entity = local.updateBeanMethod(entity);
			return BeanMethodMapper.mapperToBeanMethod(entity);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error no controlado", e);
		}
	}

	@Override
	public List<Deployment> getModule() throws ServiceException {
		try {
			List<I16dDeployment> result = local.getModule();
			return DeploymentMapper.mapperToDeploymentList(result);
		} catch (Exception e) {
			throw new ServiceException("Error no controlado", e);
		}
	}
}

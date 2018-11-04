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

import bo.union.comp.filter.ValueFilter;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import bo.union.lang.ServiceException;
import bo.union.platform.i16d.manager.DeploymentServ;
import bo.union.platform.i16d.manager.data.Deployment;
import bo.union.platform.i16d.manager.data.Version;
import bo.union.platform.i16d.manager.filter.DeploymentFtr;
import bo.union.platform.i16d.manager.local.DeploymentLocal;
import bo.union.platform.i16d.manager.entity.I16dDeployment;
import bo.union.platform.i16d.manager.entity.I16dVersion;
import bo.union.platform.i16d.manager.mapper.DeploymentMapper;
import bo.union.platform.i16d.manager.mapper.VersionMapper;

@Stateless
@Local(DeploymentServ.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DeploymentImpl implements DeploymentServ {

	@EJB
	private DeploymentLocal local;

	@Override
	public List<Deployment> filterDeployment(DeploymentFtr filter) throws ServiceException {
		try {
			filter.setType(new ValueFilter<>("PROVIDER"));
			List<I16dDeployment> result = local.filterDeployment(filter);
			return DeploymentMapper.mapperToDeploymentList(result);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error no controlado", e);
		}
	}

	@Override
	public List<Version> filterVersion(Deployment value) throws ServiceException {
		try {
			List<I16dVersion> result = local.filterVersion(value.getDeploymentId());
			return VersionMapper.mapperToVersionList(result);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error no controlado", e);
		}
	}

	@Override
	public Deployment updateDeployment(Deployment value) throws ServiceException {
		try {
			if (value.getActive() == null) {
				value.setActive(false);
			}
			if (value.getLogger() == null) {
				value.setLogger(false);
			}
			I16dDeployment entity = DeploymentMapper.mapperToI16dDeployment(value);
			entity = local.updateDeployment(entity);
			return DeploymentMapper.mapperToDeployment(entity);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error no controlado", e);
		}
	}
}

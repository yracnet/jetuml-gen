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
import bo.union.lang.ValidationException;
import bo.union.platform.i16d.manager.PrivilegeServ;
import bo.union.platform.i16d.manager.data.Bean;
import bo.union.platform.i16d.manager.data.Privilege;
import bo.union.platform.i16d.manager.entity.I16dBean;
import bo.union.platform.i16d.manager.entity.I16dDeployment;
import bo.union.platform.i16d.manager.filter.PrivilegeFtr;
import bo.union.platform.i16d.manager.local.PrivilegeLocal;
import bo.union.platform.i16d.manager.entity.I16dPrivilege;
import bo.union.platform.i16d.manager.local.BeanLocal;
import bo.union.platform.i16d.manager.local.DeploymentLocal;
import bo.union.platform.i16d.manager.mapper.PrivilegeMapper;

@Stateless
@Local(PrivilegeServ.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class PrivilegeImpl implements PrivilegeServ {

	@EJB
	private PrivilegeLocal local;
	@EJB
	private DeploymentLocal localDeploy;
	@EJB
	private BeanLocal localBean;

	@Override
	public List<Privilege> filterPrivilege(Bean bean, PrivilegeFtr filter) throws ServiceException {
		filter.setBeanId(new ValueFilter<>(bean.getBeanId()));
		List<I16dPrivilege> result = local.filterPrivilege(filter);
		return PrivilegeMapper.mapperToPrivilegeList(result);
	}

	@Override
	public Privilege createPrivilege(Privilege value) throws ServiceException {
		try {
			ValidationException validate = new ValidationException("Add Module");
			validate.isNullOrEmpty(value, "Privilege");
			validate.throwException();
			validate.isNullOrEmpty(value.getBeanId(), "beanId");
			validate.throwException();
			validate.isNullOrEmpty(value.getDeployment(), "modulo");
			validate.throwException();
			validate.isNullOrEmpty(value.getVersionId(), "version");
			validate.throwException();
			I16dPrivilege entity = PrivilegeMapper.mapperToI16dPrivilege(value);
			I16dDeployment entityDeploy = localDeploy.findDeployment(value.getDeployment().getDeploymentId());
			I16dBean entityBean = localBean.findBean(value.getBeanId());
			entity.setI16dDeployment(entityDeploy);
			entity.setI16dBean(entityBean);
			entity.setVersionId(value.getVersionId());
			entity = local.createPrivilege(entity);
			return PrivilegeMapper.mapperToPrivilege(entity);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error no controlado", e);
		}
	}

	@Override
	public Privilege updatePrivilege(Privilege value) throws ServiceException {
		I16dPrivilege entity = PrivilegeMapper.mapperToI16dPrivilege(value);
		entity = local.updatePrivilege(entity);
		return PrivilegeMapper.mapperToPrivilege(entity);
	}

	@Override
	public Privilege removePrivilege(Privilege value) throws ServiceException {
		ValidationException validate = new ValidationException("Add Module");
		validate.isNullOrEmpty(value, "Privilege");
		validate.throwException();
		I16dPrivilege entity = local.findPrivilege(value.getPrivilegeId());
		entity = local.removePrivilege(entity);
		return PrivilegeMapper.mapperToPrivilege(entity);
	}
}

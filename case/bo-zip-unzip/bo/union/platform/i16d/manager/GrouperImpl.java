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
package bo.union.platform.i16d.manager;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import bo.union.lang.ServiceException;
import bo.union.lang.ValidationException;
import bo.union.platform.i16d.manager.data.Grouper;
import bo.union.platform.i16d.manager.entity.I16dGrouper;
import bo.union.platform.i16d.manager.filter.GrouperFtr;
import bo.union.platform.i16d.manager.local.GrouperLocal;
import bo.union.platform.i16d.manager.mapper.GrouperMapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

@Stateless
@Local(GrouperServ.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class GrouperImpl implements GrouperServ {

	@EJB
	private GrouperLocal local;

	@Override
	public List<Grouper> childrenGrouper(Grouper parent, int level) throws ServiceException {
		try {
			ValidationException validate = new ValidationException("Tree Grouper");
			validate.addWhen(level > 10, "No se permite un nivel superior a 10 Hijos");
			validate.throwException();
			List<I16dGrouper> i16dGroupers = local.childrenGrouper(parent.getGrouperId());
			return processLevel(i16dGroupers, level);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error no controlado", e);
		}
	}

	private List<Grouper> processLevel(List<I16dGrouper> i16dGrouperList, int level) {
		if (level < 0 || i16dGrouperList == null) {
			return Collections.emptyList();
		}
		List<Grouper> result = new ArrayList<>();
		i16dGrouperList.forEach(i16d -> {
			Grouper grouper = GrouperMapper.mapperToGrouper(i16d);
			result.add(grouper);
			List<Grouper> children = processLevel(i16d.getI16dGrouperList(), level - 1);
			grouper.setGrouperList(children);
		});
		return result;
	}

	@Override
	public List<Grouper> filterGrouper(GrouperFtr filter) throws ServiceException {
		try {
			List<I16dGrouper> result = local.filterGrouper(filter);
			return GrouperMapper.mapperToGrouperList(result);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error no controlado", e);
		}
	}

	@Override
	public Grouper createGrouper(Grouper value) throws ServiceException {
		try {
			ValidationException validate = new ValidationException("Validacion Grouper");
			validate.isNull(value, "Grouper");
			validate.throwException();
			I16dGrouper entity = GrouperMapper.mapperToI16dGrouper(value);
			I16dGrouper parent = local.findGrouper(value.getIdParent());
			entity.setI16dParent(parent);
			String algToken = UUID.randomUUID().toString(); // 36 characters
			entity.setAlgToken(algToken);
			entity = local.createGrouper(entity);
			return GrouperMapper.mapperToGrouper(entity);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error no controlado", e);
		}
	}

	@Override
	public Grouper updateGrouper(Grouper value) throws ServiceException {
		try {
			ValidationException validate = new ValidationException("Validacion Grouper");
			validate.isNull(value, "Grouper");
			validate.throwException();
			validate.isNull(value.getGrouperId(), "grouperId");
			validate.throwException();
			I16dGrouper entity = GrouperMapper.mapperToI16dGrouper(value);
			I16dGrouper parent = local.findGrouper(value.getIdParent());
			entity.setI16dParent(parent);
			entity = local.updateGrouper(entity);
			return GrouperMapper.mapperToGrouper(entity);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error no controlado", e);
		}
	}

	@Override
	public Grouper removeGrouper(Grouper value) throws ServiceException {
		try {
			I16dGrouper entity = local.findGrouper(value.getGrouperId());
			entity = local.removeGrouper(entity);
			return GrouperMapper.mapperToGrouper(entity);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error no controlado", e);
		}
	}
}

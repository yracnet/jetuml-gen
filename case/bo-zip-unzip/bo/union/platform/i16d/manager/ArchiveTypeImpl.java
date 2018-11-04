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
import bo.union.platform.i16d.manager.data.ArchiveType;
import bo.union.platform.i16d.manager.filter.ArchiveTypeFtr;
import bo.union.platform.i16d.manager.local.ArchiveTypeLocal;
import bo.union.platform.i16d.manager.entity.I16dArchiveType;
import bo.union.platform.i16d.manager.local.ArchiveConfigLocal;
import bo.union.platform.i16d.manager.local.SendMail;
import bo.union.platform.i16d.manager.mapper.ArchiveTypeMapper;

@Stateless
@Local(ArchiveTypeServ.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ArchiveTypeImpl implements ArchiveTypeServ {

	@EJB
	private ArchiveTypeLocal local;
	@EJB
	private ArchiveConfigLocal localArchConf;

	@Override
	public List<ArchiveType> filterArchiveType(ArchiveTypeFtr filter) throws ServiceException {
		try {
			List<I16dArchiveType> result = local.filterArchiveType(filter);
			return ArchiveTypeMapper.mapperToArchiveTypeList(result);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error no controlado", e);
		}
	}

	@Override
	public ArchiveType createArchiveType(ArchiveType value) throws ServiceException {
		try {
			I16dArchiveType entity = ArchiveTypeMapper.mapperToI16dArchiveType(value);
			entity = local.createArchiveType(entity);
			return ArchiveTypeMapper.mapperToArchiveType(entity);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error no controlado", e);
		}
	}

	@Override
	public ArchiveType updateArchiveType(ArchiveType value) throws ServiceException {
		try {
			I16dArchiveType entity = ArchiveTypeMapper.mapperToI16dArchiveType(value);
			I16dArchiveType entityType = local.findArchiveType(value.getArchiveTypeId());
			entity.setI16dArchiveConfig(entityType.getI16dArchiveConfig());
			entity = local.updateArchiveType(entity);
			return ArchiveTypeMapper.mapperToArchiveType(entity);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error no controlado", e);
		}
	}

	@Override
	public ArchiveType removeArchiveType(ArchiveType value) throws ServiceException {
		ValidationException validate = new ValidationException("Remove Archive Type");
		validate.isNull(value, "ArchiveType");
		validate.throwException();
		try {
			I16dArchiveType entity = local.findArchiveType(value.getArchiveTypeId());
			validate.addWhen(entity.getI16dArchiveConfig() != null, "El tipo de archivo tiene archivo configurado!!!");
			validate.throwException();
			entity = local.removeArchiveType(entity);
			return ArchiveTypeMapper.mapperToArchiveType(entity);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error no controlado", e);
		}
	}

	@Override
	public ArchiveType sendNotification(ArchiveType value, String textBody) throws ServiceException {
		List<String> mail = value.getEmailList();
		SendMail.sendNotification(mail, textBody);
		return null;
	}

	@Override
	public ArchiveType validateArchiveType(ArchiveType value) throws ServiceException {
		ValidationException validate = new ValidationException("Validate ArchiveType");
		I16dArchiveType entity = ArchiveTypeMapper.mapperToI16dArchiveType(value);
		local.validate(entity, validate);
		int cant = local.validateUniqueCode(entity.getCode()).size();
		validate.addWhen(cant > 0, "Ya existe el code [" + entity.getCode() + "] ingrese otro code");
		validate.throwException();
		return value;
	}
}

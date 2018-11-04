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
import bo.union.platform.i16d.manager.data.Error;
import bo.union.platform.i16d.manager.data.ValueArchive;
import bo.union.platform.i16d.manager.filter.ErrorFtr;
import bo.union.platform.i16d.manager.local.ErrorLocal;
import bo.union.platform.i16d.manager.entity.I16dError;
import bo.union.platform.i16d.manager.entity.I16dValueArchive;
import bo.union.platform.i16d.manager.local.ValueArchiveLocal;
import bo.union.platform.i16d.manager.mapper.ErrorMapper;

@Stateless
@Local(ErrorServ.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ErrorImpl implements ErrorServ {

	@EJB
	private ErrorLocal local;
	@EJB
	private ValueArchiveLocal archiveLocal;

	@Override
	public List<Error> filterError(ErrorFtr filter) throws ServiceException {
		List<I16dError> result = local.filterError(filter);
		return ErrorMapper.mapperToErrorList(result);
	}

	@Override
	public List<Error> listError(ValueArchive value) throws ServiceException {
		List<I16dError> result = local.listError(value.getValueArchiveId());
		return ErrorMapper.mapperToErrorList(result);
	}

	@Override
	public Error createError(Error value) throws ServiceException {
		I16dError entity = ErrorMapper.mapperToI16dError(value);
		I16dValueArchive entityArchive = archiveLocal.findValueArchive(value.getValueArchive().getValueArchiveId());
		entity.setI16dValueArchive(entityArchive);
		entity.setAttempt(value.getAttempt());
		entity = local.createError(entity);
		return ErrorMapper.mapperToError(entity);
	}

	@Override
	public Error updateError(Error value) throws ServiceException {
		I16dError entity = ErrorMapper.mapperToI16dError(value);
		entity = local.updateError(entity);
		return ErrorMapper.mapperToError(entity);
	}

	@Override
	public Error removeError(Error value) throws ServiceException {
		I16dError entity = ErrorMapper.mapperToI16dError(value);
		entity = local.removeError(entity);
		return ErrorMapper.mapperToError(entity);
	}
}

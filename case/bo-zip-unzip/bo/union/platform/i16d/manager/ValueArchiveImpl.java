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
import bo.union.platform.i16d.manager.data.Value;
import bo.union.platform.i16d.manager.data.ValueArchive;
import bo.union.platform.i16d.manager.entity.I16dArchiveType;
import bo.union.platform.i16d.manager.entity.I16dContent;
import bo.union.platform.i16d.manager.entity.I16dGrouper;
import bo.union.platform.i16d.manager.entity.I16dValue;
import bo.union.platform.i16d.manager.filter.ValueArchiveFtr;
import bo.union.platform.i16d.manager.local.ValueArchiveLocal;
import bo.union.platform.i16d.manager.entity.I16dValueArchive;
import bo.union.platform.i16d.manager.local.ArchiveTypeLocal;
import bo.union.platform.i16d.manager.local.ContentLocal;
import bo.union.platform.i16d.manager.local.GrouperLocal;
import bo.union.platform.i16d.manager.local.ValueLocal;
import bo.union.platform.i16d.manager.mapper.ErrorMapper;
import bo.union.platform.i16d.manager.mapper.ValueArchiveMapper;
import bo.union.platform.i16d.manager.mapper.ValueMapper;

@Stateless
@Local(ValueArchiveServ.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ValueArchiveImpl implements ValueArchiveServ {

	@EJB
	private ArchiveTypeLocal typeLocal;
	@EJB
	private GrouperLocal grouperLocal;
	@EJB
	private ValueLocal valueLocal;
	@EJB
	private ValueArchiveLocal local;
	@EJB
	private ContentLocal contentLocal;

	@Override
	public List<ValueArchive> filterValueArchive(ValueArchiveFtr filter) throws ServiceException {
		try {
			List<I16dValueArchive> result = local.filterValueArchive(filter);
			return ValueArchiveMapper.mapperToValueArchiveList(result);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error no controlado", e);
		}
	}

	@Override
	public ValueArchive createValueArchive(ValueArchive valueArchive) throws ServiceException {
		try {
			I16dValueArchive entity = ValueArchiveMapper.mapperToI16dValueArchive(valueArchive);
			I16dArchiveType type = typeLocal.findArchiveType(valueArchive.getArchiveType().getArchiveTypeId());
			entity.setI16dArchiveType(type);
			I16dValue value = entity.getI16dValue();
			value.setType("ARCHIVE");
			if (value.getValueId() == null) {
				I16dGrouper grouper = grouperLocal.findGrouper(valueArchive.getValue().getGrouperId());
				value.setI16dGrouper(grouper);
				value = valueLocal.createValue(value);
				entity.setI16dValue(value);
			} else {
				value = valueLocal.findValue(value.getValueId());
				entity.setI16dValue(value);
			}
			entity.setAttemptNro(entity.getAttemptNro() + 1);
			entity = local.createValueArchive(entity);
			return ValueArchiveMapper.mapperToValueArchive(entity);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error no controlado", e);
		}
	}

	@Override
	public ValueArchive updateValueArchive(ValueArchive valueArchive) throws ServiceException {
		try {
			I16dValueArchive entity = ValueArchiveMapper.mapperToI16dValueArchive(valueArchive);
			I16dArchiveType type = typeLocal.findArchiveType(valueArchive.getArchiveType().getArchiveTypeId());
			entity.setI16dArchiveType(type);
			I16dValue value = entity.getI16dValue();
			if (value.getValueId() == null) {
				throw new ValidationException("Se requiere un value parent");
			} else {
				value = valueLocal.findValue(value.getValueId());
				entity.setI16dValue(value);
			}
			I16dContent content = contentLocal.findContent(valueArchive.getContent().getContentId());
			entity.setI16dContent(content);
			entity.setAttemptState(valueArchive.getAttemptState());
			entity.setAttemptNro(entity.getAttemptNro() + 1);
			entity.setI16dErrorList(ErrorMapper.mapperToI16dErrorList(valueArchive.getErrorList()));
			entity = local.updateValueArchive(entity);
			return ValueArchiveMapper.mapperToValueArchive(entity);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error no controlado", e);
		}
	}

	public ValueArchive update(ValueArchive valueArchive) throws ServiceException {
		try {
			I16dValueArchive entity = ValueArchiveMapper.mapperToI16dValueArchive(valueArchive);
			I16dArchiveType type = typeLocal.findArchiveType(valueArchive.getArchiveType().getArchiveTypeId());
			entity.setI16dArchiveType(type);
			I16dValue entityValue = ValueMapper.mapperToI16dValue(valueArchive.getValue());
			entity.setI16dValue(entityValue);
			I16dContent content = contentLocal.findContent(valueArchive.getContent().getContentId());
			entity.setI16dContent(content);
			entity.setDateFrom(valueArchive.getDateFrom());
			entity.setDateTo(valueArchive.getDateTo());
			entity = local.updateValueArchive(entity);
			return ValueArchiveMapper.mapperToValueArchive(entity);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error no controlado", e);
		}
	}

	@Override
	public ValueArchive updateValue(ValueArchive valueArchive) throws ServiceException {
		try {
			I16dValue entity = ValueMapper.mapperToI16dValue(valueArchive.getValue());
			entity = valueLocal.updateValue(entity);
			Value value = ValueMapper.mapperToValue(entity);
			valueArchive.setValue(value);
			update(valueArchive);
			return valueArchive;
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error no controlado", e);
		}
	}

	@Override
	public ValueArchive removeValueArchive(ValueArchive valueArchive) throws ServiceException {
		try {
			I16dValueArchive entity = local.findValueArchive(valueArchive.getValueArchiveId());
			entity = local.removeValueArchive(entity);
			return ValueArchiveMapper.mapperToValueArchive(entity);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error no controlado", e);
		}
	}
}

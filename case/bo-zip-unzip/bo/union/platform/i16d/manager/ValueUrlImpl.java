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
import bo.union.platform.i16d.manager.data.ValueUrl;
import bo.union.platform.i16d.manager.entity.I16dValue;
import bo.union.platform.i16d.manager.filter.ValueUrlFtr;
import bo.union.platform.i16d.manager.local.ValueUrlLocal;
import bo.union.platform.i16d.manager.entity.I16dValueUrl;
import bo.union.platform.i16d.manager.local.ValueLocal;
import bo.union.platform.i16d.manager.mapper.ValueUrlMapper;
import bo.union.lang.ValidationException;
import bo.union.platform.i16d.manager.data.Value;
import bo.union.platform.i16d.manager.entity.I16dGrouper;
import bo.union.platform.i16d.manager.local.GrouperLocal;
import bo.union.platform.i16d.manager.local.HelpLocal;
import bo.union.platform.i16d.manager.mapper.ValueMapper;
import java.util.regex.Matcher;

@Stateless
@Local(ValueUrlServ.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ValueUrlImpl implements ValueUrlServ {

	@EJB
	private GrouperLocal grouperLocal;
	@EJB
	private ValueUrlLocal local;
	@EJB
	private ValueLocal valueLocal;

	@Override
	public List<ValueUrl> filterValueUrl(ValueUrlFtr filter) throws ServiceException {
		try {
			List<I16dValueUrl> result = local.filterValueUrl(filter);
			return ValueUrlMapper.mapperToValueUrlList(result);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error no controlado", e);
		}
	}

	@Override
	public ValueUrl createValueUrl(ValueUrl value) throws ServiceException {
		try {
			validate(value);
			I16dValueUrl entity = ValueUrlMapper.mapperToI16dValueUrl(value);
			I16dValue valueEntity = entity.getI16dValue();
			valueEntity.setType("URL");
			if (valueEntity.getValueId() == null) {
				I16dGrouper grouper = grouperLocal.findGrouper(value.getValue().getGrouperId());
				valueEntity.setI16dGrouper(grouper);
				valueEntity = valueLocal.createValue(valueEntity);
				entity.setI16dValue(valueEntity);
			} else {
				valueEntity = valueLocal.findValue(valueEntity.getValueId());
				entity.setI16dValue(valueEntity);
			}
			entity = local.createValueUrl(entity);
			return ValueUrlMapper.mapperToValueUrl(entity);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error no controlado", e);
		}
	}

	@Override
	public ValueUrl updateValueUrl(ValueUrl value) throws ServiceException {
		try {
			validate(value);
			I16dValueUrl entity = ValueUrlMapper.mapperToI16dValueUrl(value);
			I16dValue valueEntity = entity.getI16dValue();
			if (valueEntity.getValueId() == null) {
				throw new ValidationException("Se requiere un value parent");
			} else {
				valueEntity = valueLocal.findValue(valueEntity.getValueId());
				entity.setI16dValue(valueEntity);
			}
			entity = local.updateValueUrl(entity);
			return ValueUrlMapper.mapperToValueUrl(entity);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error no controlado", e);
		}
	}

	@Override
	public ValueUrl updateValue(ValueUrl valueUrl) throws ServiceException {
		try {
			ValidationException validate = new ValidationException("Validacion Update Value Url");
			validate.isNull(valueUrl, "Value Url");
			validate.throwException();
			validate.isNull(valueUrl.getValue(), "Value");
			validate.throwException();
			I16dValue entity = ValueMapper.mapperToI16dValue(valueUrl.getValue());
			entity = valueLocal.updateValue(entity);
			Value value = ValueMapper.mapperToValue(entity);
			valueUrl.setValue(value);
			return valueUrl;
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error no controlado", e);
		}
	}

	@Override
	public ValueUrl removeValueUrl(ValueUrl value) throws ServiceException {
		try {
			ValidationException validate = new ValidationException("Validacion Remove Value Url");
			validate.isNull(value, "Value Url");
			validate.throwException();
			validate.isNull(value.getValue(), "Value");
			validate.throwException();
			I16dValueUrl entity = local.findValueUrl(value.getValueUrlId());
			I16dValue entityValue = entity.getI16dValue();
			entity = local.removeValueUrl(entity, entityValue, valueLocal);
			return ValueUrlMapper.mapperToValueUrl(entity);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error no controlado", e);
		}
	}

	public void validate(ValueUrl value) throws ValidationException {
		ValidationException validate = new ValidationException("Validacion Value Url");
		validate.isNull(value, "Value Url");
		validate.throwException();
		validate.isNull(value.getValue(), "Value");
		validate.throwException();
		validate.isNull(value.getUrlLocal(), "Url local");
		validate.isNull(value.getUrlRemote(), "Url Remoto");
		validate.throwException();
		validate.isNull(value.getConnectTimeout(), "Connect timeout");
		validate.isNull(value.getReadTimeout(), "Read timeout");
		validate.throwException();
		if (!validateUrl(value.getUrlLocal())) {
			validate.addDetail("Url Local invalido");
			validate.throwException();
		}
		if (!validateUrl(value.getUrlRemote())) {
			validate.addDetail("Url Remoto invalido");
			validate.throwException();
		}
	}

	private static boolean validateUrl(String url) {
		if (url == null) {
			return false;
		}
		Matcher matcher = HelpLocal.URL_PATTERN.matcher(url);
		return matcher.matches();
	}
}

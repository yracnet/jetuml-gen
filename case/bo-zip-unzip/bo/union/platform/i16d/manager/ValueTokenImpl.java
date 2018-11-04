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
import bo.union.platform.i16d.manager.data.ValueToken;
import bo.union.platform.i16d.manager.entity.I16dGrouper;
import bo.union.platform.i16d.manager.entity.I16dValue;
import bo.union.platform.i16d.manager.filter.ValueTokenFtr;
import bo.union.platform.i16d.manager.local.ValueTokenLocal;
import bo.union.platform.i16d.manager.entity.I16dValueToken;
import bo.union.platform.i16d.manager.local.GrouperLocal;
import bo.union.platform.i16d.manager.local.ValueLocal;
import bo.union.platform.i16d.manager.mapper.ValueMapper;
import bo.union.platform.i16d.manager.mapper.ValueTokenMapper;

@Stateless
@Local(ValueTokenServ.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ValueTokenImpl implements ValueTokenServ {

	@EJB
	private GrouperLocal grouperLocal;
	@EJB
	private ValueLocal valueLocal;
	@EJB
	private ValueTokenLocal local;

	@Override
	public List<ValueToken> filterValueToken(ValueTokenFtr filter) throws ServiceException {
		try {
			List<I16dValueToken> result = local.filterValueToken(filter);
			return ValueTokenMapper.mapperToValueTokenList(result);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error no controlado", e);
		}
	}

	@Override
	public ValueToken createValueToken(ValueToken valueToken) throws ServiceException {
		try {
			ValidationException validate = new ValidationException("Validacion Value Token");
			validate(valueToken, validate);
			I16dValueToken entity = ValueTokenMapper.mapperToI16dValueToken(valueToken);
			I16dValue valueEntity = entity.getI16dValue();
			valueEntity.setType("TOKEN");
			if (valueEntity.getValueId() == null) {
				I16dGrouper grouper = grouperLocal.findGrouper(valueToken.getValue().getGrouperId());
				valueEntity.setI16dGrouper(grouper);
				valueEntity = valueLocal.createValue(valueEntity);
				entity.setI16dValue(valueEntity);
			} else {
				valueEntity = valueLocal.findValue(valueEntity.getValueId());
				entity.setI16dValue(valueEntity);
			}
			validate.isNull(valueEntity, "Value");
			validate.throwException();
			entity = local.createValueToken(entity);
			return ValueTokenMapper.mapperToValueToken(entity);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error no controlado", e);
		}
	}

	@Override
	public ValueToken updateValueToken(ValueToken valueToken) throws ServiceException {
		try {
			ValidationException validate = new ValidationException("Validacion Value Token");
			validate(valueToken, validate);
			I16dValueToken entity = ValueTokenMapper.mapperToI16dValueToken(valueToken);
			I16dValue valueEntity = entity.getI16dValue();
			if (valueEntity.getValueId() == null) {
				throw new ValidationException("Se requiere un value parent");
			} else {
				valueEntity = valueLocal.findValue(valueEntity.getValueId());
				entity.setI16dValue(valueEntity);
			}
			validate.isNull(valueEntity, "Value");
			validate.throwException();
			entity = local.updateValueToken(entity);
			return ValueTokenMapper.mapperToValueToken(entity);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error no controlado", e);
		}
	}

	@Override
	public ValueToken updateValue(ValueToken valueToken) throws ServiceException {
		try {
			ValidationException validate = new ValidationException("Validacion Update Value Token");
			validate.isNull(valueToken, "Value Token");
			validate.throwException();
			validate.isNull(valueToken.getValue(), "Value");
			validate.throwException();
			I16dValue entity = ValueMapper.mapperToI16dValue(valueToken.getValue());
			entity = valueLocal.updateValue(entity);
			Value value = ValueMapper.mapperToValue(entity);
			valueToken.setValue(value);
			return valueToken;
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error no controlado", e);
		}
	}

	@Override
	public ValueToken removeValueToken(ValueToken valueToken) throws ServiceException {
		try {
			ValidationException validate = new ValidationException("Validacion Remove Value Token");
			validate.isNull(valueToken, "Value Token");
			validate.throwException();
			validate.isNull(valueToken.getValue(), "Value");
			validate.throwException();
			I16dValueToken entity = local.findValueToken(valueToken.getValueTokenId());
			I16dValue entityValue = entity.getI16dValue();
			entity = local.removeValueToken(entity, entityValue, valueLocal);
			return ValueTokenMapper.mapperToValueToken(entity);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error no controlado", e);
		}
	}

	private void validate(ValueToken valueToken, ValidationException validate) throws ValidationException {
		validate.isNull(valueToken, "Value Token");
		validate.throwException();
		validate.isNull(valueToken.getValue(), "Value");
		validate.throwException();
		validate.isNull(valueToken.getType(), "Tipo");
		validate.isNull(valueToken.getToken(), "Token");
		validate.throwException();
	}
}

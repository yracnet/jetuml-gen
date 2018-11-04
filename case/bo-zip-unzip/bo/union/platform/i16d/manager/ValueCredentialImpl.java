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

import bo.union.crypto.StringEncryptor;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import bo.union.lang.ServiceException;
import bo.union.lang.ValidationException;
import bo.union.platform.i16d.manager.data.PasswordValue;
import bo.union.platform.i16d.manager.data.Value;
import bo.union.platform.i16d.manager.data.ValueCredential;
import bo.union.platform.i16d.manager.entity.I16dGrouper;
import bo.union.platform.i16d.manager.entity.I16dValue;
import bo.union.platform.i16d.manager.filter.ValueCredentialFtr;
import bo.union.platform.i16d.manager.local.ValueCredentialLocal;
import bo.union.platform.i16d.manager.entity.I16dValueCredential;
import bo.union.platform.i16d.manager.local.GrouperLocal;
import bo.union.platform.i16d.manager.local.ValueLocal;
import bo.union.platform.i16d.manager.mapper.ValueCredentialMapper;
import bo.union.platform.i16d.manager.mapper.ValueMapper;

@Stateless
@Local(ValueCredentialServ.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ValueCredentialImpl implements ValueCredentialServ {

	@EJB
	private GrouperLocal grouperLocal;
	@EJB
	private ValueLocal valueLocal;
	@EJB
	private ValueCredentialLocal local;

	@Override
	public List<ValueCredential> filterValueCredential(ValueCredentialFtr filter) throws ServiceException {
		try {
			List<I16dValueCredential> result = local.filterValueCredential(filter);
			return ValueCredentialMapper.mapperToValueCredentialList(result);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error no controlado", e);
		}
	}

	@Override
	public ValueCredential createValueCredential(ValueCredential valueCredential) throws ServiceException {
		try {
			valueCredential.setNewPassword(true);
			validate(valueCredential);
			I16dValueCredential entity = ValueCredentialMapper.mapperToI16dValueCredential(valueCredential);
			I16dValue value = entity.getI16dValue();
			value.setType("CREDENTIAL");
			if (value.getValueId() == null) {
				I16dGrouper grouper = grouperLocal.findGrouper(valueCredential.getValue().getGrouperId());
				value.setI16dGrouper(grouper);
				value = valueLocal.createValue(value);
				entity.setI16dValue(value);
			} else {
				value = valueLocal.findValue(value.getValueId());
				entity.setI16dValue(value);
			}
			String password = passwordEncrypt(valueCredential.getPassword(), entity);
			entity.setPassword(password);
			entity = local.createValueCredential(entity);
			return ValueCredentialMapper.mapperToValueCredential(entity);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error no controlado", e);
		}
	}

	@Override
	public ValueCredential updateValueCredential(ValueCredential valueCredential) throws ServiceException {
		try {
			validate(valueCredential);
			I16dValueCredential entity = local.findValueCredential(valueCredential.getValueCredentialId());
			ValueCredentialMapper.mapperToI16dValueCredential(valueCredential, entity);
			I16dValue value = entity.getI16dValue();
			if (value.getValueId() == null) {
				throw new ValidationException("Se requiere un value parent");
			} else {
				value = valueLocal.findValue(value.getValueId());
				entity.setI16dValue(value);
			}
			if (valueCredential.isNewPassword()) {
				String password = passwordEncrypt(valueCredential.getPassword(), entity);
				entity.setPassword(password);
			}
			entity = local.updateValueCredential(entity);
			return ValueCredentialMapper.mapperToValueCredential(entity);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error no controlado", e);
		}
	}

	@Override
	public ValueCredential updateValue(ValueCredential valueCredential) throws ServiceException {
		try {
			ValidationException validate = new ValidationException("Validacion Update Value Credential");
			validate.isNull(valueCredential, "Value Credential");
			validate.throwException();
			validate.isNull(valueCredential.getValue(), "Value");
			validate.throwException();
			I16dValue entity = ValueMapper.mapperToI16dValue(valueCredential.getValue());
			entity = valueLocal.updateValue(entity);
			Value value = ValueMapper.mapperToValue(entity);
			valueCredential.setValue(value);
			return valueCredential;
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error no controlado", e);
		}
	}

	@Override
	public ValueCredential removeValueCredential(ValueCredential valueCredential) throws ServiceException {
		try {
			ValidationException validate = new ValidationException("Validacion Remove Value Credential");
			validate.isNull(valueCredential, "Value Credential");
			validate.throwException();
			validate.isNull(valueCredential.getValue(), "Value");
			validate.throwException();
			I16dValueCredential entity = local.findValueCredential(valueCredential.getValueCredentialId());
			I16dValue entityValue = entity.getI16dValue();
			entity = local.removeValueCredential(entity, entityValue, valueLocal);
			return ValueCredentialMapper.mapperToValueCredential(entity);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error no controlado", e);
		}
	}

	public void validate(ValueCredential value) throws ValidationException {
		ValidationException validate = new ValidationException("Validacion Value Credential");
		validate.isNull(value, "Value Credential");
		validate.throwException();
		validate.isNull(value.getValue(), "Value");
		validate.throwException();
		if (value.isNewPassword()) {
			validate.isNullOrEmpty(value.getPassword(), "Password");
			validate.throwException();
			validate.isNullOrLength(value.getPassword().getValue(), 6, 100, "Password");
			validate.throwException();
		}
	}

	public String passwordEncrypt(PasswordValue password, I16dValueCredential entity) throws ValidationException {
		if (password == null || entity == null) {
			return null;
		}
		String value = password.getValue();
		String confirm = password.getConfirm();
		if (value == null || confirm == null) {
			throw new ValidationException("Contraseña Invalida!!!");
		} else if (!value.equals(confirm)) {
			throw new ValidationException("No confirmo la contraseña!!!");
		}
		String token = entity.getI16dValue().getI16dGrouper().getAlgToken();
		return StringEncryptor.stringEncrypt(value.toCharArray(), token);
	}
}

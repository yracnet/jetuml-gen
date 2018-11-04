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
import bo.union.platform.i16d.certificate.CertificateStore;
import bo.union.platform.i16d.manager.data.PasswordValue;
import bo.union.platform.i16d.manager.data.Value;
import bo.union.platform.i16d.manager.data.ValueCertificate;
import bo.union.platform.i16d.manager.entity.I16dGrouper;
import bo.union.platform.i16d.manager.entity.I16dValue;
import bo.union.platform.i16d.manager.filter.ValueCertificateFtr;
import bo.union.platform.i16d.manager.local.ValueCertificateLocal;
import bo.union.platform.i16d.manager.entity.I16dValueCertificate;
import bo.union.platform.i16d.manager.local.GrouperLocal;
import bo.union.platform.i16d.manager.local.ValueLocal;
import bo.union.platform.i16d.manager.mapper.ValueCertificateMapper;
import bo.union.platform.i16d.manager.mapper.ValueMapper;

@Stateless
@Local(ValueCertificateServ.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ValueCertificateImpl implements ValueCertificateServ {

	@EJB
	private GrouperLocal grouperLocal;
	@EJB
	private ValueLocal valueLocal;
	@EJB
	private ValueCertificateLocal local;

	@Override
	public List<ValueCertificate> filterValueCertificate(ValueCertificateFtr filter) throws ServiceException {
		try {
			List<I16dValueCertificate> result = local.filterValueCertificate(filter);
			return ValueCertificateMapper.mapperToValueCertificateList(result);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error no controlado", e);
		}
	}

	@Override
	public ValueCertificate createValueCertificate(ValueCertificate valueCertificate) throws ServiceException {
		try {
			ValidationException validate = new ValidationException("Validacion Value Certificate");
			validate.isNull(valueCertificate, "Value Certificate");
			validate.throwException();
			validateCertificateCreate(valueCertificate, validate);
			validate.throwException();
			I16dValueCertificate entity = ValueCertificateMapper.mapperToI16dValueCertificate(valueCertificate);
			I16dValue value = entity.getI16dValue();
			value.setType("CERTIFICATE");
			if (value.getValueId() == null) {
				I16dGrouper grouper = grouperLocal.findGrouper(valueCertificate.getValue().getGrouperId());
				value.setI16dGrouper(grouper);
				value = valueLocal.createValue(value);
				entity.setI16dValue(value);
			} else {
				value = valueLocal.findValue(value.getValueId());
				entity.setI16dValue(value);
			}
			validate.isNull(value, "Value");
			validate.throwException();
			entity.setArchive(valueCertificate.getArchiveContent());
			char[] passphase = null;
			if (valueCertificate.isPrivate()) {
				passphase = valueCertificate.getPassword().valueCharArray();
				String password = passwordEncrypt(valueCertificate.getPassword(), entity);
				entity.setPassword(password);
			}
			String summary = CertificateStore.validateCertificate(valueCertificate.getArchiveContent(), passphase);
			entity.setSummary(summary);
			entity = local.createValueCertificate(entity);
			return ValueCertificateMapper.mapperToValueCertificate(entity);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error no controlado", e);
		}
	}

	@Override
	public ValueCertificate updateValueCertificate(ValueCertificate valueCertificate) throws ServiceException {
		try {
			ValidationException validate = new ValidationException("Validacion Value Certificate");
			validate.isNull(valueCertificate, "Value Certificate");
			validate.throwException();
			validateCertificateUpdate(valueCertificate, validate);
			validate.throwException();
			I16dValueCertificate entity = local.findValueCertificate(valueCertificate.getValueCertificateId());
			ValueCertificateMapper.mapperToI16dValueCertificate(valueCertificate, entity);
			I16dValue value = entity.getI16dValue();
			if (value.getValueId() == null) {
				throw new ValidationException("Se requiere un value parent");
			} else {
				value = valueLocal.findValue(value.getValueId());
				entity.setI16dValue(value);
			}
			if (valueCertificate.isNewArchive()) {
				entity.setArchive(valueCertificate.getArchiveContent());
				char[] passphase = null;
				if (valueCertificate.isPrivate()) {
					passphase = valueCertificate.getPassword().valueCharArray();
					String password = passwordEncrypt(valueCertificate.getPassword(), entity);
					entity.setPassword(password);
				}
				String summary = CertificateStore.validateCertificate(valueCertificate.getArchiveContent(), passphase);
				entity.setSummary(summary);
			}
			entity = local.updateValueCertificate(entity);
			return ValueCertificateMapper.mapperToValueCertificate(entity);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error no controlado", e);
		}
	}

	@Override
	public ValueCertificate updateValue(ValueCertificate valueCertificate) throws ServiceException {
		try {
			ValidationException validate = new ValidationException("Validacion Value Certificate");
			validate.isNull(valueCertificate, "Value Certificate");
			validate.throwException();
			validate.isNull(valueCertificate.getValue(), "Value");
			validate.throwException();
			validate.isNull(valueCertificate.getValue().getType(), "Type");
			validate.throwException();
			I16dValue entity = ValueMapper.mapperToI16dValue(valueCertificate.getValue());
			entity = valueLocal.updateValue(entity);
			Value value = ValueMapper.mapperToValue(entity);
			valueCertificate.setValue(value);
			return valueCertificate;
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error no controlado", e);
		}
	}

	@Override
	public ValueCertificate removeValueCertificate(ValueCertificate valueCertificate) throws ServiceException {
		try {
			I16dValueCertificate entity = local.findValueCertificate(valueCertificate.getValueCertificateId());
			I16dValue entityValue = entity.getI16dValue();
			entity = local.removeValueCertificate(entity, entityValue, valueLocal);
			return ValueCertificateMapper.mapperToValueCertificate(entity);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error no controlado", e);
		}
	}

	public String passwordEncrypt(PasswordValue password, I16dValueCertificate entity) throws ValidationException {
		if (password == null || entity == null) {
			return null;
		}
		String type = entity.getType();
		if (!"PRIV".equals(type)) {
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

	public void validateCertificateCreate(ValueCertificate valueCertificate, ValidationException validate) throws ValidationException {
		validate.isNull(valueCertificate.getValue(), "Value");
		validate.isNull(valueCertificate.getArchiveContent(), "Content archive");
		validate.isNull(valueCertificate.getType(), "Type");
		validate.throwException();
		if ("PRIV".equals(valueCertificate.getType())) {
			validate.isNull(valueCertificate.getAlgSignture(), "algSignture");
			validate.isNull(valueCertificate.getAlgDigest(), "algDigest");
			validate.isNull(valueCertificate.getPassword(), "Password");
		} else {
			validate.isNull(valueCertificate.getAlgEncrypt(), "algEncrypt");
		}
	}

	public void validateCertificateUpdate(ValueCertificate valueCertificate, ValidationException validate) {
		validate.isNull(valueCertificate.getValue(), "Value");
		validate.isNull(valueCertificate.getType(), "Type");
	}
}

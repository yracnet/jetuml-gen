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
package bo.union.platform.i16d.manager.local;

import bo.union.comp.FilterObject;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import bo.union.persist.qfilter.QFilter;
import bo.union.lang.ServiceException;
import bo.union.lang.ValidationException;
import bo.union.platform.i16d.manager.entity.I16dValue;
import bo.union.platform.i16d.manager.entity.I16dValueCredential;
import java.util.Date;

@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ValueCredentialLocal {

	@PersistenceContext(unitName = "i16d-manager")
	private EntityManager em;

	public List<I16dValueCredential> filterValueCredential(FilterObject filter) throws ServiceException {
		return QFilter.filter(em, I16dValueCredential.class, filter);
	}

	public I16dValueCredential findValueCredential(Long id) {
		return em.find(I16dValueCredential.class, id);
	}

	public I16dValueCredential createValueCredential(I16dValueCredential value) throws ServiceException {
		ValidationException validate = new ValidationException("Registro ValueCredential");
		validateCreateValueCredential(value, validate);
		validateExistVigencia(value, validate);
		em.persist(value);
		return value;
	}

	public I16dValueCredential updateValueCredential(I16dValueCredential entity) throws ServiceException {
		ValidationException validate = new ValidationException("Actualizar ValueCredential");
		validateUpdateValueCredential(entity, validate);
		validateExistVigencia(entity, validate);
		em.merge(entity);
		return entity;
	}

	public I16dValueCredential removeValueCredential(I16dValueCredential entity, I16dValue entityVal, ValueLocal valueLocal) throws ServiceException {
		ValidationException validate = new ValidationException("Eliminar ValueCredential");
		validateRemoveValueCredential(entity, validate);
		em.remove(entity);
		if (HelpLocal.findSameCabecera(em, "Credential", entityVal.getValueId()) == 0) {
			valueLocal.removeValue(entityVal);
		}
		return entity;
	}

	public void validateCreateValueCredential(I16dValueCredential entity, ValidationException validate) throws ServiceException {
		validate.isNull(entity, "Value Credential");
		validate.throwException();
		validate.isNotNull(entity.getValueCredentialId(), "valueCredentialId");
		validate(entity, validate);
		validate.throwException();
	}

	public void validateUpdateValueCredential(I16dValueCredential entity, ValidationException validate) throws ServiceException {
		validate.isNull(entity, "Value Credential");
		validate.throwException();
		validate.isNullOrEmpty(entity.getValueCredentialId(), "valueCredentialId");
		validate(entity, validate);
		validate.throwException();
	}

	public void validateRemoveValueCredential(I16dValueCredential value, ValidationException validate) throws ServiceException {
		validate.isNull(value, "Value Credential");
		validate.throwException();
		validate.isNullOrEmpty(value.getValueCredentialId(), "valueCredentialId");
		validate.throwException();
	}

	public void validate(I16dValueCredential entity, ValidationException validate) {
		Date from = entity.getDateFrom();
		Date to = entity.getDateTo();
		validate.isNullOrEmpty(entity.getUsername(), "Username");
		validate.isNullOrNotRegExp(entity.getUsername(), HelpLocal.REGEX_USERNAME, "Username");
		validate.isNullOrLength(entity.getUsername(), 3, 20, "Username");
		validate.isNullOrEmpty(from, "Fec Desde");
		if (from != null && to != null) {
			validate.addWhen(from.getTime() >= to.getTime(), "Fec Desde es mayor o igual a Fec Hasta");
		}
		if (to != null) {
			validate.addWhen(to.getTime() <= HelpLocal.currentDate().getTime(), "Fec Hasta es menor o igual a la fecha actual");
		}
	}

	public void validateExistVigencia(I16dValueCredential entity, ValidationException validate) throws ValidationException {
		int cantValue = HelpLocal.validateExistVigencia(em, "Credential", entity.getValueCredentialId(), entity.getI16dValue().getValueId(),
				entity.getDateFrom(), entity.getDateTo());
		if (entity.getDateFrom() != null && entity.getDateTo() != null) {
			validate.addWhen(cantValue > 0, "Existe credencial vigente para la fecha");
			validate.throwException();
		}
	}
}

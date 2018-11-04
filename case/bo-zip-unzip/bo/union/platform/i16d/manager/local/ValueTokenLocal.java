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
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import bo.union.persist.qfilter.QFilter;
import bo.union.lang.ServiceException;
import bo.union.lang.ValidationException;
import bo.union.platform.i16d.manager.entity.I16dValue;
import bo.union.platform.i16d.manager.entity.I16dValueToken;
import java.util.Date;
import javax.ejb.TransactionAttribute;
import static javax.ejb.TransactionAttributeType.REQUIRED;

@Stateless
@LocalBean
@TransactionAttribute(REQUIRED)
public class ValueTokenLocal {

	@PersistenceContext(unitName = "i16d-manager")
	private EntityManager em;

	public List<I16dValueToken> filterValueToken(FilterObject filter) throws ServiceException {
		return QFilter.filter(em, I16dValueToken.class, filter);
	}

	public I16dValueToken findValueToken(Long id) {
		return em.find(I16dValueToken.class, id);
	}

	public I16dValueToken createValueToken(I16dValueToken value) throws ServiceException {
		ValidationException validate = new ValidationException("Registro ValueToken");
		validateCreateValueToken(value, validate);
		em.persist(value);
		return value;
	}

	public I16dValueToken updateValueToken(I16dValueToken entity) throws ServiceException {
		ValidationException validate = new ValidationException("Actualizar ValueToken");
		validateUpdateValueToken(entity, validate);
		em.merge(entity);
		return entity;
	}

	public I16dValueToken removeValueToken(I16dValueToken entity, I16dValue entityVal, ValueLocal valueLocal) throws ServiceException {
		ValidationException validate = new ValidationException("Eliminar ValueToken");
		validateRemoveValueToken(entity, validate);
		em.remove(entity);
		if (HelpLocal.findSameCabecera(em, "Token", entityVal.getValueId()) == 0) {
			valueLocal.removeValue(entityVal);
		}
		return entity;
	}

	public void validateCreateValueToken(I16dValueToken entity, ValidationException validate) throws ServiceException {
		validate.isNull(entity, "Value Token");
		validate.throwException();
		validate.isNotNull(entity.getValueTokenId(), "valueTokenId");
		validate.throwException();
		validate(entity, validate);
		validateExistVigencia(entity, validate);
		validate.throwException();
	}

	public void validateUpdateValueToken(I16dValueToken entity, ValidationException validate) throws ServiceException {
		validate.isNull(entity, "Value Token");
		validate.throwException();
		validate.isNullOrEmpty(entity.getValueTokenId(), "valueTokenId");
		validate.throwException();
		validate(entity, validate);
		validate.throwException();
		validateExistVigencia(entity, validate);
		validate.throwException();
	}

	public void validateRemoveValueToken(I16dValueToken value, ValidationException validate) throws ServiceException {
		validate.isNull(value, "Value Token");
		validate.throwException();
		validate.isNullOrEmpty(value.getValueTokenId(), "valueTokenId");
		validate.throwException();
	}

	public void validate(I16dValueToken entity, ValidationException validate) {
		Date from = entity.getDateFrom();
		Date to = entity.getDateTo();
		validate.isNullOrEmpty(entity.getDateFrom(), "Fec Desde");
		if (from != null && to != null) {
			validate.addWhen(from.getTime() >= to.getTime(), "Fec Desde es mayor o igual a Fec Hasta");
		}
		if (to != null) {
			validate.addWhen(to.getTime() <= HelpLocal.currentDate().getTime(), "Fec Hasta es menor o igual a la fecha actual");
		}
		validate.isNullOrLength(entity.getToken(), 28, 40, "token");
	}

	public void validateExistVigencia(I16dValueToken entity, ValidationException validate) throws ValidationException {
		int cantValue = HelpLocal.validateExistVigencia(em, "Token", entity.getValueTokenId(), entity.getI16dValue().getValueId(), entity.getDateFrom(),
				entity.getDateTo());
		if (entity.getDateFrom() != null && entity.getDateTo() != null) {
			validate.addWhen(cantValue > 0, "Existe token vigente para la fecha");
			validate.throwException();
		}
	}
}

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

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import bo.union.persist.qfilter.QFilter;
import bo.union.lang.ServiceException;
import bo.union.lang.ValidationException;
import bo.union.comp.filter.MapFilter;
import bo.union.platform.i16d.manager.entity.I16dValue;
import bo.union.platform.i16d.manager.entity.I16dValueUrl;
import java.util.Date;

@Stateless
@LocalBean
public class ValueUrlLocal {

	@PersistenceContext(unitName = "i16d-manager")
	private EntityManager em;

	public List<I16dValueUrl> filterValueUrl(MapFilter filter) throws ServiceException {
		return QFilter.filter(em, I16dValueUrl.class, filter);
	}

	public I16dValueUrl findValueUrl(Long id) {
		return em.find(I16dValueUrl.class, id);
	}

	public I16dValueUrl createValueUrl(I16dValueUrl value) throws ServiceException {
		ValidationException validate = new ValidationException("Registro ValueUrl");
		validateCreateValueUrl(value, validate);
		em.persist(value);
		return value;
	}

	public I16dValueUrl updateValueUrl(I16dValueUrl entity) throws ServiceException {
		ValidationException validate = new ValidationException("Actualizar ValueUrl");
		validateUpdateValueUrl(entity, validate);
		em.merge(entity);
		return entity;
	}

	public I16dValueUrl removeValueUrl(I16dValueUrl entity, I16dValue entityVal, ValueLocal valueLocal) throws ServiceException {
		ValidationException validate = new ValidationException("Eliminar ValueUrl");
		validateRemoveValueUrl(entity, validate);
		em.remove(entity);
		if (HelpLocal.findSameCabecera(em, "Url", entityVal.getValueId()) == 0) {
			valueLocal.removeValue(entityVal);
		}
		return entity;
	}

	public void validateCreateValueUrl(I16dValueUrl entity, ValidationException validate) throws ServiceException {
		validate.isNull(entity, "Value Url");
		validate.throwException();
		validate.isNotNull(entity.getValueUrlId(), "valueUrlId");
		validate.throwException();
		validate(entity, validate);
		validate.throwException();
		validateExistVigencia(entity, validate);
		validate.throwException();
	}

	public void validateUpdateValueUrl(I16dValueUrl entity, ValidationException validate) throws ServiceException {
		validate.isNull(entity, "Value Url");
		validate.throwException();
		validate.isNullOrEmpty(entity.getValueUrlId(), "valueUrlId");
		validate.throwException();
		validate(entity, validate);
		validate.throwException();
		validateExistVigencia(entity, validate);
		validate.throwException();
	}

	public void validateRemoveValueUrl(I16dValueUrl value, ValidationException validate) throws ServiceException {
		validate.isNull(value, "Value Url");
		validate.throwException();
		validate.isNullOrEmpty(value.getValueUrlId(), "idValueUrl");
		validate.throwException();
	}

	public void validate(I16dValueUrl entity, ValidationException validate) throws ValidationException {
		validate.isNull(entity.getUrlLocal(), "Url Local");
		validate.isNullOrLength(entity.getUrlLocal(), 15, 100, "Url Local");
		validate.isNull(entity.getUrlRemote(), "Url Remote");
		validate.isNullOrLength(entity.getUrlRemote(), 15, 100, "Url Remote");
		validate.isNull(entity.getConnectTimeout(), "connectTimeout");
		validate.throwException();
		validate.addWhen(entity.getConnectTimeout() < 1000 || entity.getConnectTimeout() > 60000, "ConnectTimeout no esta en rango de 1000 y 60000 ms");
		validate.isNull(entity.getReadTimeout(), "readTimeout");
		validate.throwException();
		validate.addWhen(entity.getReadTimeout() < 1000 || entity.getReadTimeout() > 60000, "ReadTimeout no esta en rango de 1000 y 60000 ms");
		Date from = entity.getDateFrom();
		Date to = entity.getDateTo();
		validate.isNullOrEmpty(entity.getDateFrom(), "Fec Desde");
		if (from != null && to != null) {
			validate.addWhen(from.getTime() >= to.getTime(), "Fec Desde es mayor o igual a Fec Hasta");
		}
		if (to != null) {
			validate.addWhen(to.getTime() <= HelpLocal.currentDate().getTime(), "Fec Hasta es menor o igual a la fecha actual");
		}
	}

	public void validateExistVigencia(I16dValueUrl entity, ValidationException validate) throws ValidationException {
		int cantValue = HelpLocal.validateExistVigencia(em, "Url", entity.getValueUrlId(), entity.getI16dValue().getValueId(), entity.getDateFrom(),
				entity.getDateTo());
		if (entity.getDateFrom() != null & entity.getDateTo() != null) {
			validate.addWhen(cantValue > 0, "Existe Url vigente para la fecha");
			validate.throwException();
		}
	}
}

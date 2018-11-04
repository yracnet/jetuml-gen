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
import bo.union.platform.i16d.manager.entity.I16dValueCertificate;
import java.util.Date;

@Stateless
@LocalBean
public class ValueCertificateLocal {

	@PersistenceContext(unitName = "i16d-manager")
	private EntityManager em;

	public List<I16dValueCertificate> filterValueCertificate(MapFilter filter) throws ServiceException {
		return QFilter.filter(em, I16dValueCertificate.class, filter);
	}

	public I16dValueCertificate findValueCertificate(Long id) {
		return em.find(I16dValueCertificate.class, id);
	}

	public I16dValueCertificate createValueCertificate(I16dValueCertificate value) throws ServiceException {
		ValidationException validate = new ValidationException("Registro ValueCertificate");
		validateCreateValueCertificate(value, validate);
		validateExistVigencia(value, validate);
		em.persist(value);
		return value;
	}

	public I16dValueCertificate updateValueCertificate(I16dValueCertificate entity) throws ServiceException {
		ValidationException validate = new ValidationException("Actualizar ValueCertificate");
		validateUpdateValueCertificate(entity, validate);
		validateExistVigencia(entity, validate);
		em.merge(entity);
		return entity;
	}

	public I16dValueCertificate removeValueCertificate(I16dValueCertificate entity, I16dValue entityVal, ValueLocal valueLocal) throws ServiceException {
		ValidationException validate = new ValidationException("Eliminar ValueCertificate");
		validateRemoveValueCertificate(entity, validate);
		em.remove(entity);
		if (HelpLocal.findSameCabecera(em, "Certificate", entityVal.getValueId()) == 0) {
			valueLocal.removeValue(entityVal);
		}
		return entity;
	}

	public void validateCreateValueCertificate(I16dValueCertificate entity, ValidationException validate) throws ServiceException {
		validate.isNull(entity, "Value Certificate");
		validate.throwException();
		validate.isNotNull(entity.getValueCertificateId(), "valueCertificateId");
		validate(entity, validate);
		validate.throwException();
	}

	public void validateUpdateValueCertificate(I16dValueCertificate entity, ValidationException validate) throws ServiceException {
		validate.isNull(entity, "Value Certificate");
		validate.throwException();
		validate.isNullOrEmpty(entity.getValueCertificateId(), "valueCertificateId");
		validate(entity, validate);
		validate.throwException();
	}

	public void validateRemoveValueCertificate(I16dValueCertificate value, ValidationException validate) throws ServiceException {
		validate.isNull(value, "Value Certificate");
		validate.throwException();
		validate.isNullOrEmpty(value.getValueCertificateId(), "valueCertificateId");
		validate.throwException();
	}

	public void validate(I16dValueCertificate entity, ValidationException validate) {
		validate.isNullOrNotTextOrLength(entity.getType(), 3, 10, "Type");
		validate.isNullOrEmpty(entity.getArchive(), "Archive");
		validate.isNullOrNotCode(entity.getNameSignature(), "Name Firma");
		validate.isNullOrEmpty(entity.getDateFrom(), "Fec Desde");
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

	public void validateExistVigencia(I16dValueCertificate entity, ValidationException validate) throws ValidationException {
		int cantValue = HelpLocal.validateExistVigencia(em, "Certificate", entity.getValueCertificateId(), entity.getI16dValue().getValueId(),
				entity.getDateFrom(), entity.getDateTo());
		if (entity.getDateFrom() != null && entity.getDateTo() != null) {
			validate.addWhen(cantValue > 0, "Existe certificate vigente para la fecha");
			validate.throwException();
		}
	}
}

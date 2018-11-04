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
import bo.union.platform.i16d.manager.entity.I16dValueArchive;
import java.util.Date;

@Stateless
@LocalBean
public class ValueArchiveLocal {

	@PersistenceContext(unitName = "i16d-manager")
	private EntityManager em;

	public List<I16dValueArchive> filterValueArchive(MapFilter filter) throws ServiceException {
		return QFilter.filter(em, I16dValueArchive.class, filter);
	}

	public I16dValueArchive findValueArchive(Long id) {
		return em.find(I16dValueArchive.class, id);
	}

	public I16dValueArchive createValueArchive(I16dValueArchive value) throws ServiceException {
		ValidationException validate = new ValidationException("Registro ValueArchive");
		validateCreateValueArchive(value, validate);
		em.persist(value);
		return value;
	}

	public I16dValueArchive updateValueArchive(I16dValueArchive entity) throws ServiceException {
		ValidationException validate = new ValidationException("Actualizar ValueArchive");
		validateUpdateValueArchive(entity, validate);
		em.merge(entity);
		return entity;
	}

	public I16dValueArchive removeValueArchive(I16dValueArchive entity) throws ServiceException {
		ValidationException validate = new ValidationException("Eliminar ValueArchive");
		validateRemoveValueArchive(entity, validate);
		em.remove(entity);
		return entity;
	}

	public void validateCreateValueArchive(I16dValueArchive entity, ValidationException validate) throws ServiceException {
		validate.isNull(entity, "Value Archive");
		validate.throwException();
		validate.isNotNull(entity.getValueArchiveId(), "valueArchiveId");
		validate(entity, validate);
		validate.throwException();
	}

	public void validateUpdateValueArchive(I16dValueArchive entity, ValidationException validate) throws ServiceException {
		validate.isNull(entity, "Value Archive");
		validate.throwException();
		validate.isNullOrEmpty(entity.getValueArchiveId(), "valueArchiveId");
		validate(entity, validate);
		validate.throwException();
	}

	public void validateRemoveValueArchive(I16dValueArchive value, ValidationException validate) throws ServiceException {
		validate.isNull(value, "Value Archive");
		validate.throwException();
		validate.isNullOrEmpty(value.getValueArchiveId(), "valueArchiveId");
		validate.throwException();
	}

	public void validate(I16dValueArchive entity, ValidationException validate) {
		Date from = entity.getDateFrom();
		Date to = entity.getDateTo();
		validate.isNullOrEmpty(entity.getDateFrom(), "Fec Desde");
		if (from != null && to != null) {
			validate.addWhen(from.getTime() >= to.getTime(), "Fec Desde es mayor o igual a Fec Hasta");
		}
		if (to != null) {
			validate.addWhen(to.getTime() <= HelpLocal.currentDate().getTime(), "Fec Hasta es menor o igual a la fecha actual");
		}
		validate.isNullOrEmpty(entity.getAttemptNro(), "Attempt Nro");
		validate.isNullOrNotTextOrLength(entity.getAttemptState(), 3, 20, "Attempt State");
	}

	public void validateUpload(I16dValueArchive entity, ValueLocal valueLocal) throws ServiceException {
		ValidationException validate = new ValidationException("Upload Archive");
		validate.isNull(entity, "ValueArchive");
		validate.throwException();
		validate.isNull(entity.getDateFrom(), "dateFrom");
		validate.throwException();
		validate.isNull(entity.getI16dArchiveType(), "archiveType");
		validate.throwException();
		valueLocal.validateUploadArchive(entity.getI16dValue(), validate);
	}
}

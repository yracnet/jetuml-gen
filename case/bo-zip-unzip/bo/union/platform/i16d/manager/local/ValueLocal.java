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
import bo.union.comp.FilterObject;
import bo.union.platform.i16d.manager.entity.I16dGrouper;
import bo.union.platform.i16d.manager.entity.I16dValue;
import bo.union.platform.i16d.manager.entity.I16dValueCredential;
import bo.union.platform.i16d.manager.entity.I16dValueItem;
import java.util.Date;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;

@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ValueLocal {

	@PersistenceContext(unitName = "i16d-manager")
	private EntityManager em;

	public List<I16dValue> filterValue(FilterObject filter) throws ServiceException {
		return QFilter.filter(em, I16dValue.class, filter);
	}

	public I16dValue findValue(Long id) {
		return em.find(I16dValue.class, id);
	}

	public I16dValue createValue(I16dValue value) throws ServiceException {
		ValidationException validate = new ValidationException("Registro Value");
		validateCreateValue(value, validate);
		em.persist(value);
		return value;
	}

	public I16dValue updateValue(I16dValue entity) throws ServiceException {
		ValidationException validate = new ValidationException("Actualizar Value");
		validate.isNull(entity, "Value");
		validate.throwException();
		I16dValue old = em.find(I16dValue.class, entity.getValueId());
		I16dGrouper grouper = old.getI16dGrouper();
		validateUpdateValue(entity, validate);
		entity.setI16dGrouper(grouper);
		em.merge(entity);
		return entity;
	}

	public I16dValue removeValue(I16dValue entity) throws ServiceException {
		ValidationException validate = new ValidationException("Eliminar Value");
		validateRemoveValue(entity, validate);
		em.remove(entity);
		return entity;
	}

	public void validateCreateValue(I16dValue entity, ValidationException validate) throws ServiceException {
		validate.isNull(entity, "Value");
		validate.throwException();
		validate.isNotNull(entity.getValueId(), "valueId");
		validate(entity, validate);
		validate.throwException();
		validateUniqueNameCreate(entity, validate);
		validate.throwException();
	}

	public void validateUpdateValue(I16dValue entity, ValidationException validate) throws ServiceException {
		validate.isNull(entity, "Value");
		validate.throwException();
		validate.isNullOrEmpty(entity.getValueId(), "valueId");
		validate(entity, validate);
		validate.throwException();
		validateUniqueNameUpdate(entity, validate);
		validate.throwException();
	}

	public void validateRemoveValue(I16dValue value, ValidationException validate) throws ServiceException {
		validate.isNull(value, "Value");
		validate.throwException();
		validate.isNullOrEmpty(value.getValueId(), "valueId");
		validate.throwException();
	}

	public void validate(I16dValue entity, ValidationException validate) {
		validate.isNull(entity.getState(), "state");
		validate.isNullOrLength(entity.getName(), 3, 50, "name");
		validate.isNullOrNotRegExp(entity.getName(), HelpLocal.REGEX_NAME, "name");
		validate.isNullOrLength(entity.getDescription(), 5, 100, "description");
		validate.isNullOrNotRegExp(entity.getDescription(), HelpLocal.REGEX_DESCRIPTION, "description");
	}

	public void validateUploadArchive(I16dValue entity, ValidationException validate) throws ServiceException {
		validate.isNull(entity, "Value");
		validate.throwException();
		validate.isNull(entity.getName(), "Name");
		validate.isNullOrLength(entity.getName(), 3, 50, "name");
		validate.isNullOrNotRegExp(entity.getName(), HelpLocal.REGEX_NAME, "name");
		validate.throwException();
		validate.isNull(entity.getDescription(), "Descripcion");
		validate.isNullOrLength(entity.getDescription(), 5, 100, "description");
		validate.isNullOrNotRegExp(entity.getDescription(), HelpLocal.REGEX_DESCRIPTION, "description");
		validate.throwException();
		validate.isNull(entity.getState(), "State");
		validate.throwException();
		validate.isNull(entity.getType(), "Type");
		validate.throwException();
	}

	public void removeOneItemByValueId(Long valueId, Class<? extends I16dValueItem> classItem) throws ValidationException {
		Query q = em.createQuery("SELECT o FROM " + classItem.getName() + " o WHERE o.i16dValue.valueId = :valueId");
		q.setParameter("valueId", valueId);
		List<I16dValueCredential> list = q.getResultList();
		if (list.isEmpty()) {
			// continue
		} else if (list.size() == 1) {
			em.remove(list.get(0));
		} else {
			throw new ValidationException("Existen valuees multiples (" + list.size() + ")");
		}
	}

	public <T extends I16dValueItem> T activeValueItem(Long valueId, Class<T> classItem) {
		Query q = em
				.createQuery("SELECT o FROM " + classItem.getName() + " o WHERE o.i16dValue.valueId = :valueId and o.dateFrom <= :today  and :today <= o.dateTo");
		q.setParameter("valueId", valueId);
		q.setParameter("today", new Date());
		List<I16dValueCredential> list = q.getResultList();
		return list.isEmpty() ? null : (T) list.get(0);
	}

	private void validateUniqueNameCreate(I16dValue entity, ValidationException validate) {
		List<I16dValue> list = em.createQuery("SELECT o FROM I16dValue o WHERE o.name = :name AND o.type = :type").setParameter("name", entity.getName())
				.setParameter("type", entity.getType()).getResultList();
		if (list.size() > 0) {
			String grupos = "";
			for (I16dValue value : list) {
				grupos += value.getI16dGrouper().getName() + "; ";
			}
			validate.addDetail("Existe configuración con nombre " + entity.getName() + " en los grupos " + grupos);
		}
	}

	private void validateUniqueNameUpdate(I16dValue entity, ValidationException validate) {
		List<I16dValue> list = em.createQuery("SELECT o FROM I16dValue o WHERE o.name = :name AND o.type = :type").setParameter("name", entity.getName())
				.setParameter("type", entity.getType()).getResultList();
		if (!entity.getValueId().equals(list.get(0).getValueId())) {
			String grupos = "";
			for (I16dValue value : list) {
				grupos += value.getI16dGrouper().getName() + "; ";
			}
			validate.addDetail("Existe configuración con nombre " + entity.getName() + " en los grupos " + grupos);
		}
	}
}

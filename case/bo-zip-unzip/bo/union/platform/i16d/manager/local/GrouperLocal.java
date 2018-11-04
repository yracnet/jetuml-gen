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

import bo.union.comp.filter.MapFilter;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import bo.union.persist.qfilter.QFilter;
import bo.union.lang.ServiceException;
import bo.union.lang.ValidationException;
import bo.union.platform.i16d.manager.entity.I16dGrouper;
import java.util.logging.Logger;
import javax.ejb.TransactionAttribute;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import javax.persistence.Query;

@Stateless
@LocalBean
@TransactionAttribute(REQUIRED)
public class GrouperLocal {

	@PersistenceContext(unitName = "i16d-manager")
	private EntityManager em;
	private static final String VALUE_GROUPER = "Grouper";
	static final Logger LOGGER = Logger.getLogger(GrouperLocal.class.getName());

	public List<I16dGrouper> filterGrouper(MapFilter filter) throws ServiceException {
		return QFilter.filter(em, I16dGrouper.class, filter);
	}

	public I16dGrouper createGrouper(I16dGrouper value) throws ServiceException {
		ValidationException validate = new ValidationException("Registro Grouper");
		validateCreateGrouper(value, validate);
		em.persist(value);
		return value;
	}

	public I16dGrouper updateGrouper(I16dGrouper entity) throws ServiceException {
		ValidationException validate = new ValidationException("Actualizar Grouper");
		validateUpdateGrouper(entity, validate);
		em.merge(entity);
		return entity;
	}

	public I16dGrouper removeGrouper(I16dGrouper entity) throws ServiceException {
		ValidationException validate = new ValidationException("Eliminar Grouper");
		validateRemoveGrouper(entity, validate);
		em.remove(entity);
		return entity;
	}

	public I16dGrouper findGrouper(Long grouperId) {
		return grouperId == null ? null : em.find(I16dGrouper.class, grouperId);
	}

	public void validateCreateGrouper(I16dGrouper entity, ValidationException validate) throws ServiceException {
		validate.isNull(entity, VALUE_GROUPER);
		validate.throwException();
		validate(entity, validate);
		validate.throwException();
		validateGrouperName(entity.getName(), -1L, validate);
		validate.throwException();
	}

	public void validateUpdateGrouper(I16dGrouper entity, ValidationException validate) throws ServiceException {
		validate.isNull(entity, VALUE_GROUPER);
		validate.throwException();
		validate(entity, validate);
		validate.throwException();
		validateGrouperName(entity.getName(), entity.getGrouperId(), validate);
		validate.throwException();
	}

	public void validateRemoveGrouper(I16dGrouper entity, ValidationException validate) throws ServiceException {
		validate.isNull(entity, VALUE_GROUPER);
		validate.throwException();
		validate.isNullOrEmpty(entity.getGrouperId(), "Id");
		validate.throwException();
		I16dGrouper findEntity = em.find(I16dGrouper.class, entity.getGrouperId());
		validate.addWhen(!findEntity.isI16dGrouperEmpty(), "No se puede eliminar un elemento con hijos");
		validate.throwException();
		validate.addWhen(findConfiguration(entity.getGrouperId()) > 0, "No se puede eliminar un elemento con configuraciones");
		validate.throwException();
	}

	public List<I16dGrouper> childrenGrouper(Long parent) {
		List<I16dGrouper> i16dTree;
		if (parent == null) {
			Query q = em.createQuery("SELECT o FROM I16dGrouper o WHERE o.i16dParent IS NULL");
			i16dTree = q.getResultList();
		} else {
			I16dGrouper root = em.find(I16dGrouper.class, parent);
			i16dTree = root.getI16dGrouperList();
		}
		return i16dTree;
	}

	public void validateUniqueName(String name, ValidationException validate) {
		Query q = em.createQuery("SELECT obj FROM I16dGrouper obj WHERE obj.name =:name").setParameter("name", name);
		List<I16dGrouper> list = q.getResultList();
		validate.addWhen(!list.isEmpty(), "Ya existe el grouper con el name [" + name + "] ingrese otro name");
	}

	public void validateGrouperName(String name, Long grouperId, ValidationException validate) {
		if (grouperId == -1) {
			validateUniqueName(name, validate);
		} else {
			Query q = em.createQuery("SELECT obj FROM I16dGrouper obj WHERE obj.grouperId =:grouperId").setParameter("grouperId", grouperId);
			List<I16dGrouper> list = q.getResultList();
			I16dGrouper i16dGrouper = list.get(0);
			if (!name.equals(i16dGrouper.getName())) {
				validateUniqueName(name, validate);
			}
		}
	}

	public Long findConfiguration(Long grouperId) {
		Query q = em.createQuery("SELECT COUNT(obj) FROM I16dValue obj WHERE obj.i16dGrouper.grouperId =:grouperId").setParameter("grouperId", grouperId);
		return (Long) q.getSingleResult();
	}

	public void validate(I16dGrouper entity, ValidationException validate) {
		validate.isNullOrEmpty(entity.getName(), "Name");
		validate.isNullOrEmpty(entity.getDescription(), "Description");
		validate.isNullOrLength(entity.getName(), 3, 100, "Name");
		validate.isNullOrLength(entity.getDescription(), 10, 200, "Description");
		validate.isNullOrNotRegExp(entity.getName(), HelpLocal.REGEX_NAME_GROUPER, "Name");
		validate.isNullOrNotRegExp(entity.getDescription(), HelpLocal.REGEX_DESCRIPTION, "Description");
	}
}

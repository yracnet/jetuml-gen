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
import bo.union.platform.i16d.manager.entity.I16dContent;
import javax.persistence.Query;

@Stateless
@LocalBean
public class ContentLocal {

	@PersistenceContext(unitName = "i16d-manager")
	private EntityManager em;

	public List<I16dContent> filterContent(MapFilter filter) throws ServiceException {
		return QFilter.filter(em, I16dContent.class, filter);
	}

	public List<I16dContent> treeOld(Long contentId) {
		List<I16dContent> list = em.createQuery("SELECT o FROM I16dContent o WHERE o.contentId = :contentId").setParameter("contentId", contentId)
				.getResultList();
		return list;
	}

	public I16dContent findContent(Long id) {
		return id == null ? null : em.find(I16dContent.class, id);
	}

	public Long findArchiveConfig(Long idArchiveConfig) {
		Query q = em.createQuery("SELECT COUNT(o) FROM I16dContent o WHERE o.archiveConfigId = :idArchiveConfig").setParameter("idArchiveConfig",
				idArchiveConfig);
		return (Long) q.getSingleResult();
	}

	public I16dContent createContent(I16dContent value) throws ServiceException {
		ValidationException validate = new ValidationException("Registro Content");
		validateCreateContent(value, validate);
		em.persist(value);
		return value;
	}

	public I16dContent updateContent(I16dContent entity) throws ServiceException {
		ValidationException validate = new ValidationException("Actualizar Content");
		validateUpdateContent(entity, validate);
		em.merge(entity);
		return entity;
	}

	public I16dContent removeContent(I16dContent entity) throws ServiceException {
		ValidationException validate = new ValidationException("Eliminar Content");
		validateRemoveContent(entity, validate);
		em.remove(entity);
		return entity;
	}

	public void validateCreateContent(I16dContent entity, ValidationException validate) throws ServiceException {
		validate.isNull(entity, "Content");
		validate.throwException();
		validate.isNotNull(entity.getContentId(), "contentId");
		validate.throwException();
		validate(entity, validate);
	}

	public void validateUpdateContent(I16dContent entity, ValidationException validate) throws ServiceException {
		validate.isNull(entity, "Content");
		validate.throwException();
		validate.isNullOrEmpty(entity.getContentId(), "contentId");
		validate(entity, validate);
	}

	public void validateRemoveContent(I16dContent value, ValidationException validate) throws ServiceException {
		validate.isNull(value, "Content");
		validate.throwException();
		validate.isNullOrEmpty(value.getContentId(), "idContent");
		validate.throwException();
	}

	public void validate(I16dContent entity, ValidationException validate) throws ValidationException {
		validate.isNull(entity.getName(), "Name");
		validate.throwException();
		validate.isNull(entity.getContent(), "File");
		validate.throwException();
		validate.isNull(entity.getArchiveConfigId(), "archiveConfigId");
		validate.throwException();
	}
}

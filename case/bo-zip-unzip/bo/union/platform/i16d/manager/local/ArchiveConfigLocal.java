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
import bo.union.lang.ServiceException;
import bo.union.lang.ValidationException;
import bo.union.persist.qfilter.QFilter;
import bo.union.platform.i16d.manager.entity.I16dArchiveConfig;
import bo.union.platform.i16d.manager.entity.I16dArchiveMapper;
import java.util.ArrayList;
import javax.ejb.TransactionAttribute;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import javax.persistence.Query;

@Stateless
@LocalBean
@TransactionAttribute(REQUIRED)
public class ArchiveConfigLocal {

	@PersistenceContext(unitName = "i16d-manager")
	private EntityManager em;

	public List<I16dArchiveConfig> treeUpload(Long archiveConfigId) {
		List<I16dArchiveConfig> list = em.createQuery("SELECT o FROM I16dArchiveConfig o WHERE o.archiveConfigId = :archiveConfigId")
				.setParameter("archiveConfigId", archiveConfigId).getResultList();
		return list;
	}

	public List<I16dArchiveConfig> filterArchiveConfig(FilterObject filter) throws ServiceException {
		return QFilter.filter(em, I16dArchiveConfig.class, filter);
	}

	public I16dArchiveConfig createArchiveConfig(I16dArchiveConfig entity) throws ServiceException {
		ValidationException validate = new ValidationException("Registro ArchiveConfig");
		validateCreateArchiveConfig(entity, validate);
		if (entity.getI16dArchiveMapperList() != null) {
			List<I16dArchiveMapper> mapeoList = entity.getI16dArchiveMapperList();
			validateListMapper(mapeoList);
			for (I16dArchiveMapper mapeo : mapeoList) {
				mapeo.setName(mapeo.getName().toUpperCase());
				mapeo.setI16dArchiveConfig(entity);
				em.persist(mapeo);
			}
			em.persist(entity);
		}
		return entity;
	}

	public I16dArchiveConfig updateArchiveConfig(I16dArchiveConfig entity) throws ServiceException {
		ValidationException validate = new ValidationException("Actualizar ArchiveConfig");
		validateUpdateArchiveConfig(entity, validate);
		if (entity.getI16dArchiveMapperList() != null) {
			List<I16dArchiveMapper> mapeoList = entity.getI16dArchiveMapperList();
			validateListMapper(mapeoList);
			removeElement(mapeoList, entity.getArchiveConfigId());
			for (I16dArchiveMapper m : mapeoList) {
				m.setName(m.getName().toUpperCase());
				m.setI16dArchiveConfig(entity);
				em.merge(m);
			}
			em.merge(entity);
		}
		return entity;
	}

	public I16dArchiveConfig updateChildrenMail(I16dArchiveConfig entity) throws ServiceException {
		ValidationException validate = new ValidationException("Actualizar emails de ArchiveConfig hijos");
		validateUpdateArchiveConfig(entity, validate);
		em.merge(entity);
		return entity;
	}

	public I16dArchiveConfig removeArchiveConfig(I16dArchiveConfig entity) throws ServiceException {
		ValidationException validate = new ValidationException("Eliminar ArchiveConfig");
		validateRemoveArchiveConfig(entity, validate);
		I16dArchiveConfig archiveConfig = em.getReference(I16dArchiveConfig.class, entity.getArchiveConfigId());
		validate.addWhen(!archiveConfig.isI16dArchiveConfigEmpty(), "No se puede eliminar un archivo con detalle");
		validate.throwException();
		em.remove(archiveConfig);
		if (entity.getI16dArchiveMapperList() != null) {
			List<I16dArchiveMapper> mapeoList = entity.getI16dArchiveMapperList();
			mapeoList.stream().forEach(mapeo -> {
				I16dArchiveMapper archiveMapper = em.getReference(I16dArchiveMapper.class, mapeo.getArchiveMapperId());
				em.remove(archiveMapper);
			});
		}
		return archiveConfig;
	}

	public void removeElement(List<I16dArchiveMapper> mapeoListN, Long id) {
		Query q = em.createQuery("SELECT obj FROM I16dArchiveMapper obj WHERE obj.i16dArchiveConfig.archiveConfigId =:id").setParameter("id", id);
		List<I16dArchiveMapper> mapeoListDB = q.getResultList();
		mapeoListDB.stream().forEach(mapeo -> {
			if (!mapeoListN.contains(mapeo)) {
				I16dArchiveMapper archiveMapper = em.getReference(I16dArchiveMapper.class, mapeo.getArchiveMapperId());
				em.remove(archiveMapper);
			}
		});
	}

	public I16dArchiveConfig findArchiveConfig(Long archiveConfigId) {
		return archiveConfigId == null ? null : em.find(I16dArchiveConfig.class, archiveConfigId);
	}

	public List<I16dArchiveConfig> allArchiveConfig() {
		return em.createQuery("SELECT o FROM I16dArchiveConfig o").getResultList();
	}

	public void validateUniqueName(String name, ValidationException validate) throws ServiceException {
		Query q = em.createQuery("SELECT obj FROM I16dArchiveConfig obj WHERE obj.name =:name").setParameter("name", name);
		List<I16dArchiveConfig> list = q.getResultList();
		validate.addWhen(!list.isEmpty(), "Ya existe el archive configuración con el name [" + name + "] ingrese otro name");
		validate.throwException();
	}

	public void validateArchiveConfigName(String name, Long archiveConfigId, ValidationException validate) throws ServiceException {
		if (archiveConfigId == -1) {
			validateUniqueName(name, validate);
		} else {
			Query q = em.createQuery("SELECT obj FROM I16dArchiveConfig obj WHERE obj.archiveConfigId =:archiveConfigId").setParameter("archiveConfigId",
					archiveConfigId);
			List<I16dArchiveConfig> list = q.getResultList();
			I16dArchiveConfig i16dArchiveConfig = list.get(0);
			if (!name.equals(i16dArchiveConfig.getName())) {
				validateUniqueName(name, validate);
			}
		}
	}

	public void validateCreateArchiveConfig(I16dArchiveConfig entity, ValidationException validate) throws ServiceException {
		validate.isNull(entity, "Archive Config");
		validate.throwException();
		validate.isNotNull(entity.getArchiveConfigId(), "archiveConfigId");
		validate(entity, validate);
		validate.isNull(entity.getNameTabla(), "nameTable");
		validate.throwException();
		validate.isNullOrLength(entity.getNameTabla(), 2, 30, "nameTable");
		validate.throwException();
		validate.isNullOrNotRegExp(entity.getNameTabla(), HelpLocal.REGEX_T_C_NAME, "nameTable");
		validateUniqueNameTable(entity.getNameTabla(), validate);
		validate.throwException();
		Long archiveConfigId = -1L;
		validateArchiveConfigName(entity.getName(), archiveConfigId, validate);
	}

	public void validateUpdateArchiveConfig(I16dArchiveConfig entity, ValidationException validate) throws ServiceException {
		validate.isNull(entity, "Archive Config");
		validate.throwException();
		validate.isNullOrEmpty(entity.getArchiveConfigId(), "archiveConfigId");
		validate(entity, validate);
	}

	public void validateRemoveArchiveConfig(I16dArchiveConfig value, ValidationException validate) throws ServiceException {
		validate.isNull(value, "Archive Config");
		validate.throwException();
		validate.isNullOrEmpty(value.getArchiveConfigId(), "archiveConfigId");
		validate.throwException();
	}

	public void validate(I16dArchiveConfig entity, ValidationException validate) throws ServiceException {
		validate.isNull(entity.getName(), "Name");
		validate.isNullOrLength(entity.getName(), 3, 100, "Name");
		validate.throwException();
		validate.isNullOrNotRegExp(entity.getName(), HelpLocal.REGEX_NAME_FILE, "Name");
		validate.throwException();
		validate.isNull(entity.getProcessable(), "Procesable");
		validate.throwException();
		if (entity.getValProcedure() != null) {
			if (entity.getValProcedure().length() != 0) {
				validate.isNullOrLength(entity.getValProcedure(), 2, 100, "valProcedure");
				validate.throwException();
				validate.isNullOrNotRegExp(entity.getValProcedure(), HelpLocal.REGEX_T_C_NAME_ESP, "valProcedure");
				validate.throwException();
			}
		}
		validate.isNullOrLength(entity.getDescription(), 10, 100, "Description");
		validate.isNullOrNotRegExp(entity.getDescription(), HelpLocal.REGEX_DESCRIPTION, "Description");
		validate.throwException();
		validate.addWhen(entity.getMaxSize() < HelpLocal.FILE_MIN_SIZE || entity.getMaxSize() > HelpLocal.FILE_MAX_SIZE,
				"Tamaño del archivo incorrecto!!!");
		validate.throwException();
		validate.isNull(entity.getI16dArchiveMapperList().isEmpty(), "List Mapeo Columnas");
		validate.throwException();
	}

	public void validateUniqueNameTable(String nameTable, ValidationException validate) {
		Query q = em.createQuery("SELECT COUNT(o) FROM I16dArchiveConfig o WHERE o.nameTabla = :nameTable").setParameter("nameTable", nameTable);
		Long cant = (Long) q.getSingleResult();
		validate.addWhen(cant > 0, "Ya existe la tabla [" + nameTable + "] ingrese otro valor");
	}

	public void validateListMapper(List<I16dArchiveMapper> listMapper) throws ServiceException {
		ValidationException validate = new ValidationException("Mappeo Columnas");
		validate.addWhen(validateDuplicateValue(listMapper), "Columnas duplicadas!!!");
		validate.throwException();
		for (I16dArchiveMapper am : listMapper) {
			validateMapper(am, validate);
		}
	}

	public void validateMapper(I16dArchiveMapper entity, ValidationException validate) throws ServiceException {
		validate.isNull(entity.getName(), "Name");
		validate.throwException();
		validate.isNullOrLength(entity.getName(), 2, 50, "Name");
		validate.throwException();
		// validate.isNullOrNotRegExp(entity.getName(), HelpLocal.REGEX_T_C_NAME_ESP, "Name");
		// validate.throwException();
		validate.isNull(entity.getType(), "Type");
		validate.throwException();
		validate.isNullOrLength(entity.getType(), 2, 10, "Type");
		validate.throwException();
		validate.isNull(entity.getFormat(), "Format");
		validate.throwException();
		validate.isNullOrLength(entity.getFormat(), 2, 300, "Format");
		validate.throwException();
		validate.isNull(entity.getSizeValue(), "Size");
		validate.throwException();
		validate.addWhen(entity.getSizeValue() <= 0 || entity.getSizeValue() > 4000, "Valor del campo [size] inválido (Máx 4000)!!!");
		validate.throwException();
		validate.isNull(entity.getColArchive(), "Col Archive");
		validate.throwException();
		validate.isNullOrLength(entity.getColArchive(), 2, 30, "colArchive");
		validate.throwException();
		// validate.isNullOrNotRegExp(entity.getColArchive(), HelpLocal.REGEX_NAME_FILE, "Col Archive");
		// validate.throwException();
		validate.isNull(entity.getColTable(), "Col Table");
		validate.throwException();
		validate.isNullOrLength(entity.getColTable(), 2, 30, "colTable");
		validate.throwException();
		validate.isNullOrNotRegExp(entity.getColTable(), HelpLocal.REGEX_T_C_NAME, "Col Tabla");
		validate.throwException();
		if (entity.getOrderBy() != null) {
			validate.addWhen(Integer.parseInt(entity.getOrderBy()) <= 0 || Integer.parseInt(entity.getOrderBy()) > 200,
					"Valor del campo [orderBy] inválido (Máx 200)!!!");
			validate.throwException();
		}
		if (entity.getValueInicial() != null) {
			validate.isNullOrLength(entity.getValueInicial(), 2, 50, "valueInicial");
			validate.throwException();
		}
	}

	public static boolean validateDuplicateValue(List<I16dArchiveMapper> mapper) {
		final List<String> usedName = new ArrayList<>();
		final List<String> usedColArchive = new ArrayList<>();
		final List<String> usedColTabla = new ArrayList<>();
		for (I16dArchiveMapper mp : mapper) {
			final String name = mp.getName();
			final String colArchive = mp.getColArchive();
			final String colTabla = mp.getColTable();
			if (usedName.contains(name) || usedColArchive.contains(colArchive) || usedColTabla.contains(colTabla)) {
				return true;
			}
			usedName.add(name);
			usedColArchive.add(colArchive);
			usedColTabla.add(colTabla);
		}
		return false;
	}
}

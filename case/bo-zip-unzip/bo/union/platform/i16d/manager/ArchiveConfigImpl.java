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

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import bo.union.lang.ServiceException;
import bo.union.lang.ValidationException;
import bo.union.platform.i16d.manager.data.ArchiveConfig;
import bo.union.platform.i16d.manager.data.ArchiveType;
import bo.union.platform.i16d.manager.data.ConfigurationWrap;
import bo.union.platform.i16d.manager.local.ArchiveConfigLocal;
import bo.union.platform.i16d.manager.entity.I16dArchiveConfig;
import bo.union.platform.i16d.manager.entity.I16dArchiveType;
import bo.union.platform.i16d.manager.filter.ArchiveConfigFtr;
import bo.union.platform.i16d.manager.local.ArchiveTypeLocal;
import bo.union.platform.i16d.manager.local.ContentLocal;
import bo.union.platform.i16d.manager.mapper.ArchiveConfigMapper;
import bo.union.platform.i16d.manager.mapper.ArchiveTypeMapper;
import java.util.ArrayList;
import java.util.Collections;

@Stateless
@Local(ArchiveConfigServ.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ArchiveConfigImpl implements ArchiveConfigServ {

	@EJB
	private ArchiveConfigLocal local;
	@EJB
	private ArchiveTypeLocal localArchiveType;
	@EJB
	private ContentLocal localContent;

	@Override
	public ArchiveType findArchiveType(ArchiveType archType) throws ServiceException {
		I16dArchiveType entityType = localArchiveType.findArchiveType(archType.getArchiveTypeId());
		ArchiveType archiveType = ArchiveTypeMapper.mapperToArchiveType(entityType);
		if (entityType.getI16dArchiveConfig() != null) {
			archiveType.setArchiveConfig(entityType.getI16dArchiveConfig().getArchiveConfigId());
		}
		return archiveType;
	}

	@Override
	public List<ArchiveConfig> tree(ArchiveType archType, ArchiveConfig archConfig) throws ServiceException {
		Long idConfig = null;
		if (archType != null) {
			idConfig = archType.getArchiveConfig();
		} else if (archConfig != null) {
			idConfig = archConfig.getArchiveConfigId();
		}
		List<I16dArchiveConfig> result = local.treeUpload(idConfig);
		return processLevelFilter(result, 0);
	}

	@Override
	public List<ArchiveConfig> filterArchiveConfig(ArchiveConfigFtr filter) throws ServiceException {
		try {
			List<I16dArchiveConfig> result = local.filterArchiveConfig(filter);
			return ArchiveConfigMapper.mapperToArchiveConfigList(result);
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error no controlado", e);
		}
	}

	private List<ArchiveConfig> processLevelFilter(List<I16dArchiveConfig> archiveConfigList, int level) {
		if (level < 0 || archiveConfigList == null) {
			return Collections.emptyList();
		}
		List<ArchiveConfig> result = new ArrayList<>();
		archiveConfigList.forEach(archiveConfig -> {
			ArchiveConfig archConf = ArchiveConfigMapper.mapperToArchiveConfig(archiveConfig);
			result.add(archConf);
			List<ArchiveConfig> children = processLevelFilter(archiveConfig.getI16dArchiveConfigList(), 0);
			archConf.setArchiveConfigList(children);
		});
		return result;
	}

	@Override
	public ConfigurationWrap createArchiveConfig(ArchiveType archiveType, ArchiveConfig value) throws ServiceException {
		try {
			validate(value);
			I16dArchiveType entityArchiveType = new I16dArchiveType();
			I16dArchiveConfig entity = ArchiveConfigMapper.mapperToI16dArchiveConfig(value);
			I16dArchiveConfig parent = local.findArchiveConfig(value.getIdParent());
			entity.setI16dParent(parent);
			entity = local.createArchiveConfig(entity);
			if (archiveType != null) {
				entityArchiveType = ArchiveTypeMapper.mapperToI16dArchiveType(archiveType);
				entityArchiveType.setI16dArchiveConfig(entity);
				entityArchiveType = localArchiveType.createArchiveType(entityArchiveType);
			}
			ConfigurationWrap wrap = new ConfigurationWrap();
			wrap.setArchiveType(ArchiveTypeMapper.mapperToArchiveType(entityArchiveType));
			wrap.setArchiveConfig(ArchiveConfigMapper.mapperToArchiveConfig(entity));
			return wrap;
		} catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException("Error no controlado", e);
		}
	}

	@Override
	public ConfigurationWrap updateArchiveConfig(ArchiveType archiveType, ArchiveConfig value) throws ServiceException {
		ValidationException validate = new ValidationException("Update Archive Config");
		validate.isNull(value, "ArchiveConfig");
		validate.throwException();
		validate.isNull(value.getArchiveConfigId(), "ArchiveConfigId");
		validate.throwException();
		Long cant = localContent.findArchiveConfig(value.getArchiveConfigId());
		validate.addWhen(cant > 0, "La configuración no se puede actualizar. Existen archivos procesados con esta configuración!!!");
		validate.throwException();
		I16dArchiveType entityArchiveType = new I16dArchiveType();
		I16dArchiveConfig entity = ArchiveConfigMapper.mapperToI16dArchiveConfig(value);
		I16dArchiveConfig parent = local.findArchiveConfig(value.getIdParent());
		entity.setI16dParent(parent);
		entity = local.updateArchiveConfig(entity);
		if (archiveType != null) {
			entityArchiveType = ArchiveTypeMapper.mapperToI16dArchiveType(archiveType);
			entityArchiveType.setI16dArchiveConfig(entity);
			entityArchiveType = localArchiveType.updateArchiveType(entityArchiveType);
		}
		ConfigurationWrap wrap = new ConfigurationWrap();
		wrap.setArchiveType(ArchiveTypeMapper.mapperToArchiveType(entityArchiveType));
		wrap.setArchiveConfig(ArchiveConfigMapper.mapperToArchiveConfig(entity));
		return wrap;
	}

	@Override
	public ArchiveConfig removeArchiveConfig(ArchiveConfig value) throws ServiceException {
		ValidationException validate = new ValidationException("Remove ArchiveConfig");
		validate.isNull(value, "ArchiveConfig");
		validate.isNull(value.getArchiveConfigId(), "ArchiveConfigId");
		validate.throwException();
		Long cant = localContent.findArchiveConfig(value.getArchiveConfigId());
		validate.addWhen(cant > 0, "La configuración no se puede eliminar. Existe archivos procesados con esta configuración!!!");
		validate.throwException();
		I16dArchiveConfig entity = ArchiveConfigMapper.mapperToI16dArchiveConfig(value);
		I16dArchiveType entityType = localArchiveType.findArchiveTypeOfConfig(entity.getArchiveConfigId());
		Long archiveConfigId = entity.getArchiveConfigId();
		entity = local.removeArchiveConfig(entity);
		if (archiveConfigId.equals(entityType.getI16dArchiveConfig().getArchiveConfigId())) {
			localArchiveType.removeArchiveType(entityType);
		}
		return ArchiveConfigMapper.mapperToArchiveConfig(entity);
	}

	public void validate(ArchiveConfig config) throws ValidationException {
		ValidationException validate = new ValidationException("Validacion ArchiveConfig");
		validate.isNull(config, "ArchiveConfig");
		validate.throwException();
	}
}

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
import bo.union.platform.i16d.manager.data.Content;
import bo.union.platform.i16d.manager.data.Error;
import bo.union.platform.i16d.manager.data.ValueArchive;
import bo.union.platform.i16d.manager.entity.I16dArchiveConfig;
import bo.union.platform.i16d.manager.entity.I16dArchiveType;
import bo.union.platform.i16d.manager.filter.ContentFtr;
import bo.union.platform.i16d.manager.local.ContentLocal;
import bo.union.platform.i16d.manager.entity.I16dContent;
import bo.union.platform.i16d.manager.entity.I16dError;
import bo.union.platform.i16d.manager.entity.I16dValueArchive;
import bo.union.platform.i16d.manager.local.ArchiveConfigLocal;
import bo.union.platform.i16d.manager.local.ArchiveTypeLocal;
import bo.union.platform.i16d.manager.local.ProccessData;
import bo.union.platform.i16d.manager.local.ValueArchiveLocal;
import bo.union.platform.i16d.manager.local.ValueLocal;
import bo.union.platform.i16d.manager.mapper.ArchiveConfigMapper;
import bo.union.platform.i16d.manager.mapper.ContentMapper;
import bo.union.platform.i16d.manager.mapper.ErrorMapper;
import bo.union.platform.i16d.manager.mapper.ValueArchiveMapper;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@Local(ContentServ.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ContentImpl implements ContentServ {

	@EJB
	private ValueLocal valueLocal;
	@EJB
	private ContentLocal local;
	@EJB
	private ArchiveTypeLocal typeLocal;
	@EJB
	private ArchiveConfigLocal configLocal;
	@EJB
	private ValueArchiveLocal archiveLocal;
	private Content contentProccessed;
	private List<I16dError> listError;
	private List<Content> listCon;
	private String fileName;

	@Override
	public List<Content> filterContent(ContentFtr filter) throws ServiceException {
		List<I16dContent> result = local.filterContent(filter);
		return ContentMapper.mapperToContentList(result);
	}

	@Override
	public List<Content> getContent(Long idContents) throws ServiceException {
		List<I16dContent> listEntity = local.treeOld(idContents);
		return processLevelOld(listEntity);
	}

	@Override
	public Content createContent(Content value) throws ServiceException {
		I16dContent entity = ContentMapper.mapperToI16dContent(value);
		I16dContent parent = local.findContent(value.getIdI16dParent());
		entity.setI16dParent(parent);
		entity.setContent(value.getArchiveContent());
		entity = local.createContent(entity);
		return ContentMapper.mapperToContent(entity);
	}

	@Override
	public Content updateContent(Content value) throws ServiceException {
		I16dContent entity = ContentMapper.mapperToI16dContent(value);
		I16dContent parent = local.findContent(value.getIdI16dParent());
		entity.setI16dParent(parent);
		entity.setContent(value.getArchiveContent());
		entity = local.updateContent(entity);
		return ContentMapper.mapperToContent(entity);
	}

	@Override
	public Content removeContent(Content value) throws ServiceException {
		I16dContent entity = ContentMapper.mapperToI16dContent(value);
		entity = local.removeContent(entity);
		return ContentMapper.mapperToContent(entity);
	}

	@Override
	public List<Content> treeUpload(ValueArchive value) throws ServiceException {
		List<Content> resultContent;
		if (value.getValueArchiveId() == null) {
			I16dArchiveType entityType = typeLocal.findArchiveType(value.getArchiveType().getArchiveTypeId());
			List<I16dArchiveConfig> result = configLocal.treeUpload(entityType.getI16dArchiveConfig().getArchiveConfigId());
			resultContent = processLevelNew(result);
		} else {
			I16dValueArchive entityArch = archiveLocal.findValueArchive(value.getValueArchiveId());
			List<I16dContent> list = local.treeOld(entityArch.getI16dContent().getContentId());
			resultContent = processLevelOld(list);
		}
		return resultContent;
	}

	private List<Content> processLevelOld(List<I16dContent> contentList) {
		List<Content> result = new ArrayList<>();
		contentList.forEach(cont -> {
			Content content = ContentMapper.mapperToContent(cont);
			result.add(content);
			List<Content> children = processLevelOld(cont.getI16dContentList());
			content.setContentList(children);
		});
		return result;
	}

	private List<Content> processLevelNew(List<I16dArchiveConfig> archiveConfigList) {
		List<Content> result = new ArrayList<>();
		archiveConfigList.forEach(archiveConfig -> {
			ArchiveConfigMapper.mapperToArchiveConfig(archiveConfig);
			Content content = new Content();
			content.setName(archiveConfig.getName());
			content.setArchiveConfigId(archiveConfig.getArchiveConfigId());
			if (archiveConfig.getI16dParent() != null) {
				Content parent = new Content();
				parent.setContentId(archiveConfig.getI16dParent().getArchiveConfigId());
				content.setI16dParent(parent);
			}
			result.add(content);
			List<Content> children = processLevelNew(archiveConfig.getI16dArchiveConfigList());
			content.setContentList(children);
		});
		return result;
	}

	@Override
	public Content proccessContent(List<Content> value, ValueArchive archive) throws ServiceException {
		archive.getValue().setType("ARCHIVE");
		archiveLocal.validateUpload(ValueArchiveMapper.mapperToI16dValueArchive(archive), valueLocal);
		contentProccessed = new Content();
		value.forEach(content -> {
			try {
				this.proccessContentDetail(content);
			} catch (ServiceException ex) {
				throw new RuntimeException("Error on storing contecnt");
			}
		});
		return contentProccessed;
	}

	private void proccessContentDetail(Content content) throws ServiceException {
		Content c = this.createContent(content);
		if (local.findContent(c.getContentId()).getI16dParent() == null) {
			contentProccessed = c;
		}
		content.getContentList().forEach(cont -> {
			try {
				cont.setI16dParent(c);
				this.proccessContentDetail(cont);
			} catch (ServiceException ex) {
				throw new RuntimeException("Error on storing contecnt detail");
			}
		});
	}

	@Override
	public Content reproccesssContent(List<Content> value) throws ServiceException {
		contentProccessed = new Content();
		value.forEach(content -> {
			try {
				this.reproccesssContentDetail(content);
			} catch (ServiceException ex) {
				throw new RuntimeException("Error on storing contecnt");
			}
		});
		return contentProccessed;
	}

	private void reproccesssContentDetail(Content content) throws ServiceException {
		Content c = this.updateContent(content);
		if (local.findContent(c.getContentId()).getI16dParent() == null) {
			contentProccessed = c;
		}
		content.getContentList().forEach(cont -> {
			try {
				cont.setI16dParent(c);
				this.reproccesssContentDetail(cont);
			} catch (ServiceException ex) {
				throw new RuntimeException("Error on storing contecnt detail");
			}
		});
	}

	@Override
	public List<Error> proccessData(List<Content> listCont, Content cont, String proccessType) throws ServiceException {
		List<Content> listContent;
		if ("upload".equals(proccessType)) {
			listCon = new ArrayList<>();
			listCon = listCont;
			List<I16dContent> listEntity = local.treeOld(cont.getContentId());
			listContent = processLevelOld(listEntity);
		} else {
			listContent = listCont;
		}
		listError = new ArrayList<>();
		listContent.forEach(content -> {
			try {
				I16dContent entityContent = local.findContent(content.getContentId());
				I16dArchiveConfig entityConfig = configLocal.findArchiveConfig(entityContent.getArchiveConfigId());
				I16dArchiveType entityType = typeLocal.findArchiveTypeOfConfig(entityConfig.getArchiveConfigId());
				this.proccessDataDetail(content, content.getContentId(), entityType.getFormat(), entityType.getBehavior(), proccessType);
			} catch (ServiceException ex) {
				throw new RuntimeException("Error on storing contecnt");
			}
		});
		return ErrorMapper.mapperToErrorList(listError);
	}

	private void proccessDataDetail(Content content, Long contentId, String format, String behavior, String proccessType) throws ServiceException {
		I16dArchiveConfig entityConfig = configLocal.findArchiveConfig(content.getArchiveConfigId());
		I16dContent entityContent = local.findContent(content.getContentId());
		fileName = null;
		if ("upload".equals(proccessType)) {
			findNameFile(listCon, content.getArchiveConfigId());
		} else {
			fileName = content.getContent().getName();
		}
		listError = ProccessData.proccessData(entityConfig, entityContent.getContent(), contentId + "", format, behavior, proccessType, fileName,
				listError);
		content.getContentList().forEach(cont -> {
			try {
				this.proccessDataDetail(cont, cont.getContentId(), format, behavior, proccessType);
			} catch (ServiceException ex) {
				throw new RuntimeException("Error on storing contecnt detail");
			}
		});
	}

	private List<Content> findNameFile(List<Content> contentList, Long idArchiveConfig) throws ServiceException {
		List<Content> result = new ArrayList<>();
		contentList.forEach(cont -> {
			try {
				if (idArchiveConfig.equals(cont.getArchiveConfigId())) {
					fileName = cont.getContent().getName();
				}
				result.add(cont);
				List<Content> children = findNameFile(cont.getContentList(), idArchiveConfig);
				cont.setContentList(children);
			} catch (ServiceException ex) {
				Logger.getLogger(ContentImpl.class.getName()).log(Level.SEVERE, null, ex);
			}
		});
		return result;
	}
}

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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.union.platform.i16d.manager.wrapper;

import bo.union.platform.i16d.manager.data.ArchiveConfig;
import bo.union.platform.i16d.manager.data.ArchiveType;
import bo.union.platform.i16d.manager.data.Content;
import bo.union.platform.i16d.manager.data.ValueArchive;
import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sipaco
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class ArchiveWrap implements Serializable {

	private static final long serialVersionUID = 1L;
	@XmlElement
	private ArchiveType parent;
	@XmlElement
	private List<ArchiveConfig> listConfig;
	@XmlElement
	private ArchiveConfig current;
	@XmlElement
	private Content content;
	@XmlElement
	private List<Content> listContent;
	@XmlElement
	private ValueArchive valueArchive;
	@XmlElement
	private Long archiveConfigId;

	public ValueArchive getValueArchive() {
		return valueArchive;
	}

	public void setValueArchive(ValueArchive valueArchive) {
		this.valueArchive = valueArchive;
	}

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	public List<Content> getListContent() {
		return listContent;
	}

	public void setListContent(List<Content> listContent) {
		this.listContent = listContent;
	}

	public ArchiveType getParent() {
		return parent;
	}

	public void setParent(ArchiveType parent) {
		this.parent = parent;
	}

	public List<ArchiveConfig> getListConfig() {
		return listConfig;
	}

	public void setListConfig(List<ArchiveConfig> list) {
		this.listConfig = list;
	}

	public ArchiveConfig getCurrent() {
		return current;
	}

	public void setCurrent(ArchiveConfig current) {
		this.current = current;
	}

	public Long getArchiveConfigId() {
		return archiveConfigId;
	}

	public void setArchiveConfigId(Long archiveConfigId) {
		this.archiveConfigId = archiveConfigId;
	}
}

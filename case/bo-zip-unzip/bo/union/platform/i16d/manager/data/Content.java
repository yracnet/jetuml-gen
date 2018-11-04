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
package bo.union.platform.i16d.manager.data;

import bo.union.comp.FileValue;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import bo.union.police.PoliceBase;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Content extends PoliceBase implements Serializable {

	private static final long serialVersionUID = 1L;
	@XmlElement
	private Long archiveConfigId;
	@XmlElement
	private String name;
	@XmlElement
	private FileValue content;
	@XmlElement
	private String summary;
	@XmlElement
	private Content i16dParent;
	@XmlElement
	private Long contentId;
	@XmlElement
	private List<Content> contentList;

	public Long getArchiveConfigId() {
		return archiveConfigId;
	}

	public void setArchiveConfigId(Long value) {
		archiveConfigId = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String value) {
		name = value;
	}

	public byte[] getArchiveContent() {
		return content == null ? null : content.getContent();
	}

	public FileValue getContent() {
		return content;
	}

	public void setContent(FileValue value) {
		this.content = value;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String value) {
		summary = value;
	}

	public Long getIdI16dParent() {
		return i16dParent == null ? null : i16dParent.contentId;
	}

	public Content getI16dParent() {
		return i16dParent;
	}

	public void setI16dParent(Content value) {
		this.i16dParent = value;
	}

	public Long getContentId() {
		return contentId;
	}

	public void setContentId(Long value) {
		contentId = value;
	}

	public List<Content> getContentList() {
		return contentList;
	}

	public void setContentList(List<Content> contentList) {
		this.contentList = contentList;
	}

	public void addContent(Content content) {
		if (contentList == null) {
			contentList = new ArrayList<>();
		}
		contentList.add(content);
	}
}

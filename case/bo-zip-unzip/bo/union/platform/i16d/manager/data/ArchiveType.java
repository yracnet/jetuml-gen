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

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import bo.union.police.PoliceBase;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class ArchiveType extends PoliceBase implements Serializable {

	private static final long serialVersionUID = 1L;
	@XmlElement
	private String code;
	@XmlElement
	private String origin;
	@XmlElement
	private String sender;
	@XmlElement
	private String format;
	@XmlElement
	private String description;
	@XmlElement
	private Long archiveConfig;
	@XmlElement
	private List<String> emailList;
	@XmlElement
	private Long archiveTypeId;
	@XmlElement
	private String behavior;

	public String getBehavior() {
		return behavior;
	}

	public void setBehavior(String behavior) {
		this.behavior = behavior;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String value) {
		code = value;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String value) {
		origin = value;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String value) {
		sender = value;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String value) {
		format = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String value) {
		description = value;
	}

	public Long getArchiveConfig() {
		return archiveConfig;
	}

	public void setArchiveConfig(Long value) {
		archiveConfig = value;
	}

	public List<String> getEmailList() {
		return emailList;
	}

	public void setEmailList(List<String> emailList) {
		this.emailList = emailList;
	}

	public Long getArchiveTypeId() {
		return archiveTypeId;
	}

	public void setArchiveTypeId(Long value) {
		archiveTypeId = value;
	}
}

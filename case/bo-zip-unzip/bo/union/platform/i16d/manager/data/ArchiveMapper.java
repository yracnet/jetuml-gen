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

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class ArchiveMapper extends PoliceBase implements Serializable {

	private static final long serialVersionUID = 1L;
	@XmlElement
	private String colTable;
	@XmlElement
	private String colArchive;
	@XmlElement
	private String type;
	@XmlElement
	private String name;
	@XmlElement
	private String format;
	@XmlElement
	private String isRequired;
	@XmlElement
	private String isInsertable;
	@XmlElement
	private String valueInicial;
	@XmlElement
	private String orderBy;
	@XmlElement
	private Integer sizeValue;
	@XmlElement
	private String archiveConfig;
	@XmlElement
	private Long archiveMapperId;

	public String getColTable() {
		return colTable;
	}

	public void setColTable(String value) {
		colTable = value;
	}

	public String getColArchive() {
		return colArchive;
	}

	public void setColArchive(String value) {
		colArchive = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String value) {
		type = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String value) {
		name = value;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String value) {
		format = value;
	}

	public String getIsRequired() {
		return isRequired;
	}

	public void setIsRequired(String value) {
		isRequired = value;
	}

	public String getIsInsertable() {
		return isInsertable;
	}

	public void setIsInsertable(String value) {
		isInsertable = value;
	}

	public String getValueInicial() {
		return valueInicial;
	}

	public void setValueInicial(String value) {
		valueInicial = value;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String value) {
		orderBy = value;
	}

	public Integer getSizeValue() {
		return sizeValue;
	}

	public void setSizeValue(Integer value) {
		sizeValue = value;
	}

	public String getArchiveConfig() {
		return archiveConfig;
	}

	public void setArchiveConfig(String value) {
		archiveConfig = value;
	}

	public Long getArchiveMapperId() {
		return archiveMapperId;
	}

	public void setArchiveMapperId(Long value) {
		archiveMapperId = value;
	}
}

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
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class ArchiveConfig extends PoliceBase implements Serializable {

	private static final long serialVersionUID = 1L;
	@XmlElement
	private String name;
	@XmlElement
	private String description;
	@XmlElement
	private String valProcedure;
	@XmlElement
	private String processable;
	@XmlElement
	private Long maxSize;
	@XmlElement
	private String nameTabla;
	@XmlElement
	private String csvSplit;
	@XmlElement
	private String csvHead;
	@XmlElement
	private String csvComma;
	@XmlElement
	private String xlsSheet;
	@XmlElement
	private String xlsHead;
	@XmlElement
	private ArchiveConfig parent;
	@XmlElement
	private Long archiveConfigId;
	@XmlElement
	private List<ArchiveMapper> mapeoList;
	@XmlElement
	private List<ArchiveConfig> archiveConfigList;

	public String getName() {
		return name;
	}

	public void setName(String value) {
		name = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String value) {
		description = value;
	}

	public String getValProcedure() {
		return valProcedure;
	}

	public void setValProcedure(String valProcedure) {
		this.valProcedure = valProcedure;
	}

	public String getProcessable() {
		return processable;
	}

	public void setProcessable(String value) {
		processable = value;
	}

	public Long getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(Long value) {
		maxSize = value;
	}

	public String getNameTabla() {
		return nameTabla;
	}

	public void setNameTabla(String value) {
		nameTabla = value;
	}

	public String getCsvSplit() {
		return csvSplit;
	}

	public void setCsvSplit(String value) {
		csvSplit = value;
	}

	public String getCsvHead() {
		return csvHead;
	}

	public void setCsvHead(String value) {
		csvHead = value;
	}

	public String getCsvComma() {
		return csvComma;
	}

	public void setCsvComma(String value) {
		csvComma = value;
	}

	public String getXlsSheet() {
		return xlsSheet;
	}

	public void setXlsSheet(String value) {
		xlsSheet = value;
	}

	public String getXlsHead() {
		return xlsHead;
	}

	public void setXlsHead(String value) {
		xlsHead = value;
	}

	public Long getIdParent() {
		return parent == null ? null : parent.archiveConfigId;
	}

	public ArchiveConfig getParent() {
		return parent;
	}

	public void setParent(ArchiveConfig parent) {
		this.parent = parent;
	}

	public Long getArchiveConfigId() {
		return archiveConfigId;
	}

	public void setArchiveConfigId(Long value) {
		archiveConfigId = value;
	}

	public List<ArchiveMapper> getMapeoList() {
		return mapeoList;
	}

	public void setMapeoList(List<ArchiveMapper> mapeoList) {
		this.mapeoList = mapeoList;
	}

	public List<ArchiveConfig> getArchiveConfigList() {
		return archiveConfigList;
	}

	public void setArchiveConfigList(List<ArchiveConfig> archiveConfigList) {
		this.archiveConfigList = archiveConfigList;
	}

	public void addArchiveConfig(ArchiveConfig archiveConfig) {
		if (archiveConfigList == null) {
			archiveConfigList = new ArrayList<>();
		}
		archiveConfigList.add(archiveConfig);
	}
}

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
/**
 * This file was generated by the Jeddict
 */
package bo.union.platform.i16d.manager.entity;

import bo.union.persist.police.AuditPartial;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author sipaco
 */
@Entity
@Table(name = "I16D_ARCHIVE_CONFIG")
@Cacheable(false)
public class I16dArchiveConfig extends AuditPartial {

	@Column(name = "ARCHIVE_CONFIG_ID", columnDefinition = "NUMBER")
	@Id
	@GeneratedValue(generator = "I16D_SEQ_ARCHIVE_CONFIG", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "I16D_SEQ_ARCHIVE_CONFIG", sequenceName = "I16D_SEQ_ARCHIVE_CONFIG")
	private Long archiveConfigId;
	@Column(name = "NAME_", columnDefinition = "VARCHAR2(100)", length = 100)
	@Basic
	private String name;
	@Column(name = "DESCRIPTION", columnDefinition = "VARCHAR2(100)", length = 100)
	@Basic
	private String description;
	@Column(name = "VAL_PROCEDURE", columnDefinition = "VARCHAR2(500)", length = 500)
	@Basic
	private String valProcedure;
	@Column(columnDefinition = "VARCHAR2(10)", length = 10)
	@Basic
	private String processable;
	@Column(name = "MAX_SIZE", columnDefinition = "NUMBER")
	@Basic
	private Long maxSize;
	@Column(name = "NAME_TABLA", columnDefinition = "VARCHAR2(30)", length = 30)
	@Basic
	private String nameTabla;
	@Column(name = "CSV_SPLIT", columnDefinition = "VARCHAR2(10)", length = 10)
	@Basic
	private String csvSplit;
	@Column(name = "CSV_HEAD", columnDefinition = "VARCHAR2(10)", length = 10)
	@Basic
	private String csvHead;
	@Column(name = "CSV_COMMA", columnDefinition = "VARCHAR2(10)", length = 10)
	@Basic
	private String csvComma;
	@Column(name = "XLS_SHEET", columnDefinition = "VARCHAR2(10)", length = 10)
	@Basic
	private String xlsSheet;
	@Column(name = "XLS_HEAD", columnDefinition = "VARCHAR2(10)", length = 10)
	@Basic
	private String xlsHead;
	@ManyToOne(targetEntity = I16dArchiveConfig.class)
	@JoinColumn(name = "ARCHIVE_CONFIG_PARENT_ID")
	private I16dArchiveConfig i16dParent;
	@OneToMany(targetEntity = I16dArchiveConfig.class, mappedBy = "i16dParent")
	private List<I16dArchiveConfig> i16dArchiveConfigList;
	@OneToMany(targetEntity = I16dArchiveMapper.class, mappedBy = "i16dArchiveConfig")
	private List<I16dArchiveMapper> i16dArchiveMapperList;

	public Long getArchiveConfigId() {
		return this.archiveConfigId;
	}

	public void setArchiveConfigId(Long archiveConfigId) {
		this.archiveConfigId = archiveConfigId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getValProcedure() {
		return valProcedure;
	}

	public void setValProcedure(String valProcedure) {
		this.valProcedure = valProcedure;
	}

	public String getProcessable() {
		return this.processable;
	}

	public void setProcessable(String processable) {
		this.processable = processable;
	}

	public Long getMaxSize() {
		return this.maxSize;
	}

	public void setMaxSize(Long maxSize) {
		this.maxSize = maxSize;
	}

	public String getNameTabla() {
		return this.nameTabla;
	}

	public void setNameTabla(String nameTabla) {
		this.nameTabla = nameTabla;
	}

	public String getCsvSplit() {
		return this.csvSplit;
	}

	public void setCsvSplit(String csvSplit) {
		this.csvSplit = csvSplit;
	}

	public String getCsvHead() {
		return this.csvHead;
	}

	public void setCsvHead(String csvHead) {
		this.csvHead = csvHead;
	}

	public String getCsvComma() {
		return this.csvComma;
	}

	public void setCsvComma(String csvComma) {
		this.csvComma = csvComma;
	}

	public String getXlsSheet() {
		return this.xlsSheet;
	}

	public void setXlsSheet(String xlsSheet) {
		this.xlsSheet = xlsSheet;
	}

	public String getXlsHead() {
		return this.xlsHead;
	}

	public void setXlsHead(String xlsHead) {
		this.xlsHead = xlsHead;
	}

	public I16dArchiveConfig getI16dParent() {
		return this.i16dParent;
	}

	public void setI16dParent(I16dArchiveConfig i16dParent) {
		this.i16dParent = i16dParent;
	}

	public List<I16dArchiveConfig> getI16dArchiveConfigList() {
		return this.i16dArchiveConfigList;
	}

	public void setI16dArchiveConfigList(List<I16dArchiveConfig> i16dArchiveConfigList) {
		this.i16dArchiveConfigList = i16dArchiveConfigList;
	}

	public List<I16dArchiveMapper> getI16dArchiveMapperList() {
		return this.i16dArchiveMapperList;
	}

	public void setI16dArchiveMapperList(List<I16dArchiveMapper> i16dArchiveMapperList) {
		this.i16dArchiveMapperList = i16dArchiveMapperList;
	}

	public boolean isI16dArchiveConfigEmpty() {
		return i16dArchiveConfigList == null || i16dArchiveConfigList.isEmpty();
	}
}

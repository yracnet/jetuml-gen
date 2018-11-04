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
package bo.union.platform.i16d.manager.filter;

import java.io.Serializable;
import bo.union.comp.FilterElement;
import bo.union.comp.filter.MapFilter;
import bo.union.comp.filter.ValueFilter;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ArchiveConfigFtr extends MapFilter implements Serializable {

	private static final long serialVersionUID = 1L;
	@FilterElement
	private ValueFilter<Long> archiveConfigId;
	@FilterElement
	private ValueFilter<String> name;
	@FilterElement
	private ValueFilter<String> format;
	@FilterElement
	private ValueFilter<String> description;
	@FilterElement
	private ValueFilter<String> behavior;
	@FilterElement
	private ValueFilter<String> processable;
	@FilterElement
	private ValueFilter<String> maxSize;
	@FilterElement
	private ValueFilter<String> nameTabla;
	@FilterElement
	private ValueFilter<String> csvSplit;
	@FilterElement
	private ValueFilter<String> csvHead;
	@FilterElement
	private ValueFilter<String> csvComma;
	@FilterElement
	private ValueFilter<String> xlsSheet;
	@FilterElement
	private ValueFilter<String> xlsHead;
	@FilterElement
	private ValueFilter<String> emailNotification;

	public ValueFilter<Long> getArchiveConfigId() {
		return archiveConfigId;
	}

	public void setArchiveConfigId(ValueFilter<Long> value) {
		archiveConfigId = value;
	}

	public ValueFilter<String> getName() {
		return name;
	}

	public void setName(ValueFilter<String> value) {
		name = value;
	}

	public ValueFilter<String> getFormat() {
		return format;
	}

	public void setFormat(ValueFilter<String> value) {
		format = value;
	}

	public ValueFilter<String> getDescription() {
		return description;
	}

	public void setDescription(ValueFilter<String> value) {
		description = value;
	}

	public ValueFilter<String> getBehavior() {
		return behavior;
	}

	public void setBehavior(ValueFilter<String> value) {
		behavior = value;
	}

	public ValueFilter<String> getProcessable() {
		return processable;
	}

	public void setProcessable(ValueFilter<String> value) {
		processable = value;
	}

	public ValueFilter<String> getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(ValueFilter<String> value) {
		maxSize = value;
	}

	public ValueFilter<String> getNameTabla() {
		return nameTabla;
	}

	public void setNameTabla(ValueFilter<String> value) {
		nameTabla = value;
	}

	public ValueFilter<String> getCsvSplit() {
		return csvSplit;
	}

	public void setCsvSplit(ValueFilter<String> value) {
		csvSplit = value;
	}

	public ValueFilter<String> getCsvHead() {
		return csvHead;
	}

	public void setCsvHead(ValueFilter<String> value) {
		csvHead = value;
	}

	public ValueFilter<String> getCsvComma() {
		return csvComma;
	}

	public void setCsvComma(ValueFilter<String> value) {
		csvComma = value;
	}

	public ValueFilter<String> getXlsSheet() {
		return xlsSheet;
	}

	public void setXlsSheet(ValueFilter<String> value) {
		xlsSheet = value;
	}

	public ValueFilter<String> getXlsHead() {
		return xlsHead;
	}

	public void setXlsHead(ValueFilter<String> value) {
		xlsHead = value;
	}

	public ValueFilter<String> getEmailNotification() {
		return emailNotification;
	}

	public void setEmailNotification(ValueFilter<String> value) {
		emailNotification = value;
	}
}

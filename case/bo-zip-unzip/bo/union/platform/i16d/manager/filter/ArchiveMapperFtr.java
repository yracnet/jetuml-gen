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
public class ArchiveMapperFtr extends MapFilter implements Serializable {

	private static final long serialVersionUID = 1L;
	@FilterElement
	private ValueFilter<Long> archiveMapperId;
	@FilterElement
	private ValueFilter<String> colTable;
	@FilterElement
	private ValueFilter<String> colArchive;
	@FilterElement
	private ValueFilter<String> type;
	@FilterElement
	private ValueFilter<String> name;
	@FilterElement
	private ValueFilter<String> format;
	@FilterElement
	private ValueFilter<String> isRequired;
	@FilterElement
	private ValueFilter<String> isInsertable;
	@FilterElement
	private ValueFilter<String> valueInicial;
	@FilterElement
	private ValueFilter<String> orderBy;
	@FilterElement
	private ValueFilter<Integer> sizeValue;

	public ValueFilter<Long> getArchiveMapperId() {
		return archiveMapperId;
	}

	public void setArchiveMapperId(ValueFilter<Long> value) {
		archiveMapperId = value;
	}

	public ValueFilter<String> getColTable() {
		return colTable;
	}

	public void setColTable(ValueFilter<String> value) {
		colTable = value;
	}

	public ValueFilter<String> getColArchive() {
		return colArchive;
	}

	public void setColArchive(ValueFilter<String> value) {
		colArchive = value;
	}

	public ValueFilter<String> getType() {
		return type;
	}

	public void setType(ValueFilter<String> value) {
		type = value;
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

	public ValueFilter<String> getIsRequired() {
		return isRequired;
	}

	public void setIsRequired(ValueFilter<String> value) {
		isRequired = value;
	}

	public ValueFilter<String> getIsInsertable() {
		return isInsertable;
	}

	public void setIsInsertable(ValueFilter<String> value) {
		isInsertable = value;
	}

	public ValueFilter<String> getValueInicial() {
		return valueInicial;
	}

	public void setValueInicial(ValueFilter<String> value) {
		valueInicial = value;
	}

	public ValueFilter<String> getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(ValueFilter<String> value) {
		orderBy = value;
	}

	public ValueFilter<Integer> getSizeValue() {
		return sizeValue;
	}

	public void setSizeValue(ValueFilter<Integer> value) {
		sizeValue = value;
	}
}

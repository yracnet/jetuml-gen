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
public class ArchiveTypeFtr extends MapFilter implements Serializable {

	private static final long serialVersionUID = 1L;
	@FilterElement
	private ValueFilter<Long> archiveTypeId;
	@FilterElement
	private ValueFilter<String> code;
	@FilterElement
	private ValueFilter<String> origin;
	@FilterElement
	private ValueFilter<String> sender;
	@FilterElement
	private ValueFilter<String> format;
	@FilterElement
	private ValueFilter<String> description;

	public ValueFilter<Long> getArchiveTypeId() {
		return archiveTypeId;
	}

	public void setArchiveTypeId(ValueFilter<Long> value) {
		archiveTypeId = value;
	}

	public ValueFilter<String> getCode() {
		return code;
	}

	public void setCode(ValueFilter<String> value) {
		code = value;
	}

	public ValueFilter<String> getOrigin() {
		return origin;
	}

	public void setOrigin(ValueFilter<String> value) {
		origin = value;
	}

	public ValueFilter<String> getSender() {
		return sender;
	}

	public void setSender(ValueFilter<String> value) {
		sender = value;
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
}

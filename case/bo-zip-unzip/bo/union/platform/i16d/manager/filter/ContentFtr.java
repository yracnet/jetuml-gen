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
public class ContentFtr extends MapFilter implements Serializable {

	private static final long serialVersionUID = 1L;
	@FilterElement
	private ValueFilter<Long> contentId;
	@FilterElement
	private ValueFilter<Long> archiveConfigId;
	@FilterElement
	private ValueFilter<String> name;
	@FilterElement
	private ValueFilter<byte[]> content;
	@FilterElement
	private ValueFilter<String> summary;

	public ValueFilter<Long> getContentId() {
		return contentId;
	}

	public void setContentId(ValueFilter<Long> value) {
		contentId = value;
	}

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

	public ValueFilter<byte[]> getContent() {
		return content;
	}

	public void setContent(ValueFilter<byte[]> value) {
		content = value;
	}

	public ValueFilter<String> getSummary() {
		return summary;
	}

	public void setSummary(ValueFilter<String> value) {
		summary = value;
	}
}
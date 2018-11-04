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

import java.util.Date;
import java.io.Serializable;
import bo.union.lang.ValidationException;
import bo.union.comp.FilterElement;
import bo.union.comp.filter.ValueFilter;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
// public class ValueUrlFtr extends MapFilter implements Serializable {
public class ValueUrlFtr extends ValueFtr implements ValueItemFtr, Serializable {

	private static final long serialVersionUID = 1L;
	@FilterElement
	private ValueFilter<Long> valueUrlId;
	@FilterElement
	private ValueFilter<String> urlLocal;
	@FilterElement
	private ValueFilter<String> urlRemote;
	@FilterElement
	private ValueFilter<Long> connectTimeout;
	@FilterElement
	private ValueFilter<Long> readTimeout;
	@FilterElement
	private ValueFilter<String> isRegex;
	@FilterElement
	private ValueFilter<Date> dateFrom;
	@FilterElement
	private ValueFilter<Date> dateTo;

	public ValueFilter<Long> getValueUrlId() {
		return valueUrlId;
	}

	public void setValueUrlId(ValueFilter<Long> value) {
		valueUrlId = value;
	}

	public ValueFilter<Long> getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(ValueFilter<Long> value) {
		connectTimeout = value;
	}

	public ValueFilter<Long> getReadTimeout() {
		return readTimeout;
	}

	public void setReadTimeout(ValueFilter<Long> value) {
		readTimeout = value;
	}

	public ValueFilter<String> getUrlLocal() {
		return urlLocal;
	}

	public void setUrlLocal(ValueFilter<String> value) {
		urlLocal = value;
	}

	public ValueFilter<String> getUrlRemote() {
		return urlRemote;
	}

	public void setUrlRemote(ValueFilter<String> value) {
		urlRemote = value;
	}

	public ValueFilter<String> getIsRegex() {
		return isRegex;
	}

	public void setIsRegex(ValueFilter<String> value) {
		isRegex = value;
	}

	public ValueFilter<Date> getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(ValueFilter<Date> value) {
		dateFrom = value;
	}

	public ValueFilter<Date> getDateTo() {
		return dateTo;
	}

	public void setDateTo(ValueFilter<Date> value) {
		dateTo = value;
	}

	public void validate(ValidationException validate) throws ValidationException {
	}
}
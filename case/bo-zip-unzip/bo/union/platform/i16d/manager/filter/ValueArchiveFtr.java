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
import bo.union.comp.FilterElement;
import bo.union.comp.filter.ValueFilter;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ValueArchiveFtr extends ValueFtr implements ValueItemFtr, Serializable {

	private static final long serialVersionUID = 1L;
	@FilterElement
	private ValueFilter<Long> valueArchiveId;
	@FilterElement
	private ValueFilter<Integer> attemptNro;
	@FilterElement
	private ValueFilter<String> attemptState;
	@FilterElement
	private ValueFilter<Date> dateFrom;
	@FilterElement
	private ValueFilter<Date> dateTo;

	public ValueFilter<Long> getValueArchiveId() {
		return valueArchiveId;
	}

	public void setValueArchiveId(ValueFilter<Long> value) {
		valueArchiveId = value;
	}

	public ValueFilter<Integer> getAttemptNro() {
		return attemptNro;
	}

	public void setAttemptNro(ValueFilter<Integer> value) {
		attemptNro = value;
	}

	public ValueFilter<String> getAttemptState() {
		return attemptState;
	}

	public void setAttemptState(ValueFilter<String> value) {
		attemptState = value;
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
}

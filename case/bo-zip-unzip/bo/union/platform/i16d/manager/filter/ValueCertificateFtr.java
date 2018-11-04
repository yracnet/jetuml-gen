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
public class ValueCertificateFtr extends ValueFtr implements Serializable {

	private static final long serialVersionUID = 1L;
	@FilterElement
	private ValueFilter<Long> valueCertificateId;
	@FilterElement
	private ValueFilter<String> type;
	@FilterElement
	private ValueFilter<String> nameSignature;
	@FilterElement
	private ValueFilter<Date> dateFrom;
	@FilterElement
	private ValueFilter<Date> dateTo;

	public ValueFilter<Long> getValueCertificateId() {
		return valueCertificateId;
	}

	public void setValueCertificateId(ValueFilter<Long> value) {
		valueCertificateId = value;
	}

	public ValueFilter<String> getType() {
		return type;
	}

	public void setType(ValueFilter<String> value) {
		type = value;
	}

	public ValueFilter<String> getNameSignature() {
		return nameSignature;
	}

	public void setNameSignature(ValueFilter<String> value) {
		nameSignature = value;
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

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

import java.util.Date;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import bo.union.police.PoliceBase;

@XmlRootElement(name = "url")
@XmlAccessorType(XmlAccessType.NONE)
public class ValueUrl extends PoliceBase implements Serializable {

	private static final long serialVersionUID = 1L;
	@XmlElement
	private String urlLocal;
	@XmlElement
	private String urlRemote;
	@XmlElement
	private String isRegex;
	@XmlElement(type = Date.class)
	private Date dateFrom;
	@XmlElement(type = Date.class)
	private Date dateTo;
	@XmlElement
	private Value value;
	@XmlElement
	private Long valueUrlId;
	@XmlElement
	private Long connectTimeout;
	@XmlElement
	private Long readTimeout;

	public String getUrlLocal() {
		return urlLocal;
	}

	public void setUrlLocal(String value) {
		urlLocal = value;
	}

	public String getUrlRemote() {
		return urlRemote;
	}

	public void setUrlRemote(String value) {
		urlRemote = value;
	}

	public String getIsRegex() {
		return isRegex;
	}

	public void setIsRegex(String value) {
		isRegex = value;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date value) {
		dateFrom = value;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date value) {
		dateTo = value;
	}

	public Value getValue() {
		return value;
	}

	public void setValue(Value value) {
		this.value = value;
	}

	public Long getValueUrlId() {
		return valueUrlId;
	}

	public void setValueUrlId(Long value) {
		valueUrlId = value;
	}

	public Long getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(Long connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public Long getReadTimeout() {
		return readTimeout;
	}

	public void setReadTimeout(Long readTimeout) {
		this.readTimeout = readTimeout;
	}
}

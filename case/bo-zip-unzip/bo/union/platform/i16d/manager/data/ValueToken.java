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

@XmlRootElement(name = "token")
@XmlAccessorType(XmlAccessType.NONE)
public class ValueToken extends PoliceBase implements ValueItem, Serializable {

	public static final String NAME = "token";
	private static final long serialVersionUID = 1L;
	@XmlElement
	private String token;
	@XmlElement(type = Date.class)
	private Date dateFrom;
	@XmlElement(type = Date.class)
	private Date dateTo;
	@XmlElement
	private String type;
	@XmlElement
	private Value value;
	@XmlElement
	private Long valueTokenId;

	public String getToken() {
		return token;
	}

	public void setToken(String value) {
		token = value;
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

	public String getType() {
		return type;
	}

	public void setType(String value) {
		type = value;
	}

	@Override
	public Value getValue() {
		return value;
	}

	@Override
	public void setValue(Value value) {
		this.value = value;
	}

	public Long getValueTokenId() {
		return valueTokenId;
	}

	public void setValueTokenId(Long value) {
		valueTokenId = value;
	}
}

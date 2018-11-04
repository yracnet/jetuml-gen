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
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author wyujra
 */
@Entity
@Table(name = "I16D_VALUE_URL")
public class I16dValueUrl extends AuditPartial implements I16dValueItem {

	@Column(name = "VALUE_URL_ID", columnDefinition = "NUMBER", updatable = false, nullable = false)
	@Id
	@GeneratedValue(generator = "I16D_SEQ_VALUE_URL", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "I16D_SEQ_VALUE_URL", sequenceName = "I16D_SEQ_VALUE_URL")
	private Long valueUrlId;
	@Column(name = "URL_LOCAL", columnDefinition = "VARCHAR2(100)", length = 100)
	@Basic
	private String urlLocal;
	@Column(name = "URL_REMOTE", columnDefinition = "VARCHAR2(100)", length = 100)
	@Basic
	private String urlRemote;
	@Column(name = "CONNECT_TIMEOUT", columnDefinition = "NUMBER")
	@Basic
	private Long connectTimeout;
	@Column(name = "READ_TIMEOUT", columnDefinition = "NUMBER")
	@Basic
	private Long readTimeout;
	@Column(name = "IS_REGEX", columnDefinition = "VARCHAR2(1)", length = 1)
	@Basic
	private String isRegex;
	@Column(name = "DATE_FROM", columnDefinition = "TIMESTAMP", nullable = false)
	@Basic(optional = false)
	@Temporal(TemporalType.DATE)
	private Date dateFrom;
	@Column(name = "DATE_TO", columnDefinition = "TIMESTAMP", nullable = false)
	@Basic(optional = false)
	@Temporal(TemporalType.DATE)
	private Date dateTo;
	@ManyToOne(targetEntity = I16dValue.class)
	@JoinColumn(name = "VALUE_ID")
	private I16dValue i16dValue;

	public Long getValueUrlId() {
		return this.valueUrlId;
	}

	public void setValueUrlId(Long valueUrlId) {
		this.valueUrlId = valueUrlId;
	}

	public String getUrlLocal() {
		return this.urlLocal;
	}

	public void setUrlLocal(String urlLocal) {
		this.urlLocal = urlLocal;
	}

	public String getUrlRemote() {
		return this.urlRemote;
	}

	public void setUrlRemote(String urlRemote) {
		this.urlRemote = urlRemote;
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

	public String getIsRegex() {
		return this.isRegex;
	}

	public void setIsRegex(String isRegex) {
		this.isRegex = isRegex;
	}

	public Date getDateFrom() {
		return this.dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return this.dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public I16dValue getI16dValue() {
		return this.i16dValue;
	}

	public void setI16dValue(I16dValue i16dValue) {
		this.i16dValue = i16dValue;
	}
}
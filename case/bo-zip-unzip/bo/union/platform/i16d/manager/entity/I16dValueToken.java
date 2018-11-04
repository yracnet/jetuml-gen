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
@Table(name = "I16D_VALUE_TOKEN")
public class I16dValueToken extends AuditPartial implements I16dValueItem {

	@Column(name = "VALUE_TOKEN_ID", columnDefinition = "NUMBER", updatable = false, nullable = false)
	@Id
	@GeneratedValue(generator = "I16D_SEQ_VALUE_TOKEN", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "I16D_SEQ_VALUE_TOKEN", sequenceName = "I16D_SEQ_VALUE_TOKEN", allocationSize = 10)
	private Long valueTokenId;
	@Column(name = "TOKEN", columnDefinition = "VARCHAR2(40)", length = 40)
	@Basic
	private String token;
	@Column(name = "DATE_FROM", columnDefinition = "TIMESTAMP", nullable = false)
	@Basic(optional = false)
	@Temporal(TemporalType.DATE)
	private Date dateFrom;
	@Column(name = "DATE_TO", columnDefinition = "TIMESTAMP", nullable = false)
	@Basic(optional = false)
	@Temporal(TemporalType.DATE)
	private Date dateTo;
	@Column(name = "TYPE_", columnDefinition = "VARCHAR2(10)", length = 10)
	@Basic
	private String type;
	@ManyToOne(targetEntity = I16dValue.class)
	@JoinColumn(name = "VALUE_ID")
	private I16dValue i16dValue;

	public Long getValueTokenId() {
		return this.valueTokenId;
	}

	public void setValueTokenId(Long valueTokenId) {
		this.valueTokenId = valueTokenId;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
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

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public I16dValue getI16dValue() {
		return this.i16dValue;
	}

	public void setI16dValue(I16dValue i16dValue) {
		this.i16dValue = i16dValue;
	}
}

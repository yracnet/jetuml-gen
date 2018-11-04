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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author sipaco
 */
@Entity
@Table(name = "I16D_PRIVILEGE")
public class I16dPrivilege extends AuditPartial {

	@Column(name = "PRIVILEGE_ID", columnDefinition = "NUMBER", updatable = false, nullable = false)
	@Id
	@GeneratedValue(generator = "I16D_SEQ_PRIVILEGE", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "I16D_SEQ_PRIVILEGE", sequenceName = "I16D_SEQ_PRIVILEGE")
	private Long privilegeId;
	@Column(name = "VERSION_ID", columnDefinition = "VARCHAR2(100)", nullable = false, length = 100)
	@Basic
	private String versionId;
	@OneToOne(targetEntity = I16dDeployment.class)
	@JoinColumn(name = "DEPLOYMENT_ID")
	private I16dDeployment i16dDeployment;
	@OneToOne(targetEntity = I16dBean.class)
	@JoinColumn(name = "BEAN_ID")
	private I16dBean i16dBean;

	public Long getPrivilegeId() {
		return this.privilegeId;
	}

	public void setPrivilegeId(Long privilegeId) {
		this.privilegeId = privilegeId;
	}

	public I16dDeployment getI16dDeployment() {
		return this.i16dDeployment;
	}

	public void setI16dDeployment(I16dDeployment i16dDeployment) {
		this.i16dDeployment = i16dDeployment;
	}

	public I16dBean getI16dBean() {
		return this.i16dBean;
	}

	public void setI16dBean(I16dBean i16dBean) {
		this.i16dBean = i16dBean;
	}

	public String getVersionId() {
		return versionId;
	}

	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}
}
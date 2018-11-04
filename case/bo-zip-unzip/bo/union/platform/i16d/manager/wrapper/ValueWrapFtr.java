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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.union.platform.i16d.manager.wrapper;

import bo.union.platform.i16d.manager.filter.ValueCredentialFtr;
import bo.union.platform.i16d.manager.filter.ValueItemFtr;
import bo.union.platform.i16d.manager.filter.ValueTokenFtr;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author wyujra
 */
@XmlRootElement
public class ValueWrapFtr {

	@XmlElement
	private String type;
	@XmlElement
	private Long grouperId;
	@XmlElement
	private ValueCredentialFtr credential;
	@XmlElement
	private ValueTokenFtr token;

	public ValueItemFtr getFilter() {
		if ("credential".equals(type)) {
			return credential == null ? new ValueCredentialFtr() : credential;
		} else if ("token".equals(type)) {
			return token == null ? new ValueTokenFtr() : token;
		}
		return null;
	}

	public Long getGrouperId() {
		return grouperId;
	}

	public void setGrouperId(Long grouperId) {
		this.grouperId = grouperId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ValueCredentialFtr getCredential() {
		return credential;
	}

	public void setCredential(ValueCredentialFtr credential) {
		this.credential = credential;
	}

	public ValueTokenFtr getToken() {
		return token;
	}

	public void setToken(ValueTokenFtr token) {
		this.token = token;
	}
}

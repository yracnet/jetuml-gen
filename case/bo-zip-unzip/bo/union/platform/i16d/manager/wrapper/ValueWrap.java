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

import bo.union.platform.i16d.manager.data.Value;
import bo.union.platform.i16d.manager.data.ValueCredential;
import bo.union.platform.i16d.manager.data.ValueItem;
import bo.union.platform.i16d.manager.data.ValueToken;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author wyujra
 */
@XmlRootElement
public class ValueWrap {

	@XmlElement
	private Value value;
	@XmlElement
	private ValueCredential credential;
	@XmlElement
	private ValueToken token;

	public Value getValue() {
		return value;
	}

	public void setValue(Value value) {
		this.value = value;
	}

	public ValueItem getItem() {
		String type = value.getType();
		if (ValueCredential.NAME.equals(type)) {
			return credential;
		} else if (ValueToken.NAME.equals(type)) {
			return token;
		}
		return null;
	}

	public ValueCredential getCredential() {
		return credential;
	}

	public void setCredential(ValueCredential credential) {
		this.credential = credential;
	}

	public ValueToken getToken() {
		return token;
	}

	public void setToken(ValueToken token) {
		this.token = token;
	}
}

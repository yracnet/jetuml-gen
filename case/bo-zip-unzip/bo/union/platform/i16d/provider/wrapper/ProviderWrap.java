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
package bo.union.platform.i16d.provider.wrapper;

import bo.union.platform.i16d.manager.data.Bean;
import bo.union.platform.i16d.manager.filter.PrivilegeFtr;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sipaco
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class ProviderWrap implements Serializable {

	private static final long serialVersionUID = 1L;
	@XmlElement
	private Bean bean;
	@XmlElement
	private PrivilegeFtr privilegeFtr;

	public Bean getBean() {
		return bean;
	}

	public void setBean(Bean bean) {
		this.bean = bean;
	}

	public PrivilegeFtr getPrivilegeFtr() {
		return privilegeFtr;
	}

	public void setPrivilegeFtr(PrivilegeFtr privilegeFtr) {
		this.privilegeFtr = privilegeFtr;
	}
}

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

import java.io.Serializable;
import bo.union.lang.ValidationException;
import bo.union.comp.FilterElement;
import bo.union.comp.FilterObject;
import bo.union.comp.filter.MapFilter;
import bo.union.comp.filter.ValueFilter;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class PrivilegeFtr extends MapFilter implements FilterObject, Serializable {

	private static final long serialVersionUID = 1L;
	@FilterElement
	private ValueFilter<Long> privilegeId;
	@FilterElement(name = "i16dBean.beanId")
	private ValueFilter<String> beanId;

	public ValueFilter<Long> getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(ValueFilter<Long> value) {
		privilegeId = value;
	}

	public ValueFilter<String> getBeanId() {
		return beanId;
	}

	public void setBeanId(ValueFilter<String> beanId) {
		this.beanId = beanId;
	}

	public void validate(ValidationException validate) throws ValidationException {
	}
}

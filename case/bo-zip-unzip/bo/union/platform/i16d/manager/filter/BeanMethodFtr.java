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
import bo.union.comp.FilterElement;
import bo.union.comp.filter.MapFilter;
import bo.union.comp.filter.ValueFilter;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class BeanMethodFtr extends MapFilter implements Serializable {

	private static final long serialVersionUID = 1L;
	@FilterElement
	private ValueFilter<String> beanMethodId;
	@FilterElement
	private ValueFilter<String> beanId;
	@FilterElement
	private ValueFilter<String> name;
	@FilterElement
	private ValueFilter<String> description;
	@FilterElement
	private ValueFilter<String> methodSignature;
	@FilterElement
	private ValueFilter<String> beanRef;
	@FilterElement
	private ValueFilter<String> beanName;
	@FilterElement
	private ValueFilter<Boolean> active;

	public ValueFilter<String> getBeanMethodId() {
		return beanMethodId;
	}

	public void setBeanMethodId(ValueFilter<String> value) {
		beanMethodId = value;
	}

	public ValueFilter<String> getBeanId() {
		return beanId;
	}

	public void setBeanId(ValueFilter<String> value) {
		beanId = value;
	}

	public ValueFilter<String> getName() {
		return name;
	}

	public void setName(ValueFilter<String> value) {
		name = value;
	}

	public ValueFilter<String> getDescription() {
		return description;
	}

	public void setDescription(ValueFilter<String> value) {
		description = value;
	}

	public ValueFilter<String> getMethodSignature() {
		return methodSignature;
	}

	public void setMethodSignature(ValueFilter<String> value) {
		methodSignature = value;
	}

	public ValueFilter<String> getBeanRef() {
		return beanRef;
	}

	public void setBeanRef(ValueFilter<String> value) {
		beanRef = value;
	}

	public ValueFilter<String> getBeanName() {
		return beanName;
	}

	public void setBeanName(ValueFilter<String> value) {
		beanName = value;
	}

	public ValueFilter<Boolean> getActive() {
		return active;
	}

	public void setActive(ValueFilter<Boolean> value) {
		active = value;
	}
}
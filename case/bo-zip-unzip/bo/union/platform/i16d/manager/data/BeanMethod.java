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

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import bo.union.police.PoliceBase;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class BeanMethod extends PoliceBase implements Serializable {

	private static final long serialVersionUID = 1L;
	@XmlElement
	private String beanId;
	@XmlElement
	private String name;
	@XmlElement
	private String description;
	@XmlElement
	private String methodSignature;
	@XmlElement
	private String beanRef;
	@XmlElement
	private String beanName;
	@XmlElement
	private Boolean active;
	@XmlElement
	private String beanMethodId;

	public String getBeanId() {
		return beanId;
	}

	public void setBeanId(String value) {
		beanId = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String value) {
		name = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String value) {
		description = value;
	}

	public String getMethodSignature() {
		return methodSignature;
	}

	public void setMethodSignature(String value) {
		methodSignature = value;
	}

	public String getBeanRef() {
		return beanRef;
	}

	public void setBeanRef(String value) {
		beanRef = value;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String value) {
		beanName = value;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean value) {
		active = value;
	}

	public String getBeanMethodId() {
		return beanMethodId;
	}

	public void setBeanMethodId(String value) {
		beanMethodId = value;
	}
}
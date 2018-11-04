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
public class Value extends PoliceBase implements Serializable {

	private static final long serialVersionUID = 1L;
	@XmlElement
	private Long valueId;
	@XmlElement
	private Long grouperId;
	@XmlElement
	private String name;
	@XmlElement
	private String type;
	@XmlElement
	private String state;
	@XmlElement
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String value) {
		name = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String value) {
		type = value;
	}

	public String getState() {
		return state;
	}

	public void setState(String value) {
		state = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String value) {
		description = value;
	}

	public Long getValueId() {
		return valueId;
	}

	public void setValueId(Long value) {
		valueId = value;
	}

	public Long getGrouperId() {
		return grouperId;
	}

	public void setGrouperId(Long grouperId) {
		this.grouperId = grouperId;
	}
}

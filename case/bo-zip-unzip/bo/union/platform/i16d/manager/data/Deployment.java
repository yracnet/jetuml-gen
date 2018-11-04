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
public class Deployment extends PoliceBase implements Serializable {

	private static final long serialVersionUID = 1L;
	@XmlElement
	private String name;
	@XmlElement
	private String description;
	@XmlElement
	private String type;
	@XmlElement
	private String subtype;
	@XmlElement
	private Boolean active;
	@XmlElement
	private Boolean logger;
	@XmlElement
	private String versionId;
	@XmlElement
	private String deploymentId;

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

	public String getType() {
		return type;
	}

	public void setType(String value) {
		type = value;
	}

	public String getSubtype() {
		return subtype;
	}

	public void setSubtype(String value) {
		subtype = value;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean value) {
		active = value;
	}

	public Boolean getLogger() {
		return logger;
	}

	public void setLogger(Boolean value) {
		logger = value;
	}

	public String getVersionId() {
		return versionId;
	}

	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}

	public String getDeploymentId() {
		return deploymentId;
	}

	public void setDeploymentId(String value) {
		deploymentId = value;
	}
}

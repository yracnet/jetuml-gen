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

import java.util.Date;
import java.io.Serializable;
import bo.union.lang.ValidationException;
import bo.union.comp.FilterElement;
import bo.union.comp.filter.MapFilter;
import bo.union.comp.filter.ValueFilter;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class DeploymentFtr extends MapFilter implements Serializable {

	private static final long serialVersionUID = 1L;
	@FilterElement
	private ValueFilter<String> deploymentId;
	@FilterElement
	private ValueFilter<String> name;
	@FilterElement
	private ValueFilter<String> description;
	@FilterElement
	private ValueFilter<String> type;
	@FilterElement
	private ValueFilter<String> subtype;
	@FilterElement
	private ValueFilter<Boolean> active;
	@FilterElement
	private ValueFilter<Boolean> logger;
	@FilterElement
	private ValueFilter<String> version;

	public ValueFilter<String> getDeploymentId() {
		return deploymentId;
	}

	public void setDeploymentId(ValueFilter<String> value) {
		deploymentId = value;
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

	public ValueFilter<String> getType() {
		return type;
	}

	public void setType(ValueFilter<String> value) {
		type = value;
	}

	public ValueFilter<String> getSubtype() {
		return subtype;
	}

	public void setSubtype(ValueFilter<String> value) {
		subtype = value;
	}

	public ValueFilter<Boolean> getActive() {
		return active;
	}

	public void setActive(ValueFilter<Boolean> value) {
		active = value;
	}

	public ValueFilter<Boolean> getLogger() {
		return logger;
	}

	public void setLogger(ValueFilter<Boolean> value) {
		logger = value;
	}

	public ValueFilter<String> getVersion() {
		return version;
	}

	public void setVersion(ValueFilter<String> value) {
		version = value;
	}

	public void validate(ValidationException validate) throws ValidationException {
	}
}
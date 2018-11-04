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
import bo.union.comp.FilterObject;
import bo.union.comp.filter.MapFilter;
import bo.union.comp.filter.Operator;
import bo.union.comp.filter.ValueFilter;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ValueFtr extends MapFilter implements FilterObject, Serializable {

	private static final long serialVersionUID = 1L;
	@FilterElement(name = "i16dValue.valueId")
	private ValueFilter<Long> valueId;
	@FilterElement(name = "i16dValue.i16dGrouper.grouperId")
	private ValueFilter<Long> grouperId;
	@FilterElement(name = "i16dValue.name")
	private ValueFilter<String> name;
	@FilterElement(name = "i16dValue.state")
	private ValueFilter<String> state;
	@FilterElement(name = "i16dValue.description")
	private ValueFilter<String> description;

	public ValueFilter<Long> getValueId() {
		return valueId;
	}

	public void setValueId(ValueFilter<Long> value) {
		valueId = value;
	}

	public ValueFilter<String> getName() {
		return name;
	}

	public void setName(ValueFilter<String> value) {
		name = value;
	}

	public ValueFilter<String> getState() {
		return state;
	}

	public void setState(ValueFilter<String> value) {
		state = value;
	}

	public ValueFilter<String> getDescription() {
		return description;
	}

	public void setDescription(ValueFilter<String> value) {
		description = value;
	}

	public Long getGrouperId() {
		return grouperId == null ? null : grouperId.getFirstValue();
	}

	public void setGrouperId(Long value) {
		grouperId = new ValueFilter();
		grouperId.setOper(Operator.EQUALS);
		grouperId.addValue(value);
	}
}

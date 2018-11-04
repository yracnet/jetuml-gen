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
import bo.union.comp.filter.MapFilter;
import bo.union.comp.filter.ValueFilter;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ErrorFtr extends MapFilter implements Serializable {

	private static final long serialVersionUID = 1L;
	@FilterElement
	private ValueFilter<Long> errorId;
	@FilterElement
	private ValueFilter<String> attempt;
	@FilterElement
	private ValueFilter<String> description;
	@FilterElement
	private ValueFilter<String> nomArchive;
	@FilterElement
	private ValueFilter<String> nameColumn;
	@FilterElement
	private ValueFilter<Integer> atLine;

	public ValueFilter<Long> getErrorId() {
		return errorId;
	}

	public void setErrorId(ValueFilter<Long> value) {
		errorId = value;
	}

	public ValueFilter<String> getAttempt() {
		return attempt;
	}

	public void setAttempt(ValueFilter<String> value) {
		attempt = value;
	}

	public ValueFilter<String> getDescription() {
		return description;
	}

	public void setDescription(ValueFilter<String> value) {
		description = value;
	}

	public ValueFilter<String> getNomArchive() {
		return nomArchive;
	}

	public void setNomArchive(ValueFilter<String> value) {
		nomArchive = value;
	}

	public ValueFilter<String> getNameColumn() {
		return nameColumn;
	}

	public void setNameColumn(ValueFilter<String> value) {
		nameColumn = value;
	}

	public ValueFilter<Integer> getAtLine() {
		return atLine;
	}

	public void setAtLine(ValueFilter<Integer> value) {
		atLine = value;
	}

	public void validate(ValidationException validate) throws ValidationException {
	}
}
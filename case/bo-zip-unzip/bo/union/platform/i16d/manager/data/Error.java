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
public class Error extends PoliceBase implements Serializable {

	private static final long serialVersionUID = 1L;
	@XmlElement
	private String attempt;
	@XmlElement
	private String description;
	@XmlElement
	private String nomArchive;
	@XmlElement
	private String nameColumn;
	@XmlElement
	private Integer atLine;
	@XmlElement
	private Long errorId;
	@XmlElement
	private ValueArchive valueArchive;

	public String getAttempt() {
		return attempt;
	}

	public void setAttempt(String value) {
		attempt = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String value) {
		description = value;
	}

	public String getNomArchive() {
		return nomArchive;
	}

	public void setNomArchive(String value) {
		nomArchive = value;
	}

	public String getNameColumn() {
		return nameColumn;
	}

	public void setNameColumn(String value) {
		nameColumn = value;
	}

	public Integer getAtLine() {
		return atLine;
	}

	public void setAtLine(Integer value) {
		atLine = value;
	}

	public Long getErrorId() {
		return errorId;
	}

	public void setErrorId(Long value) {
		errorId = value;
	}

	public ValueArchive getValueArchive() {
		return valueArchive;
	}

	public void setValueArchive(ValueArchive valueArchive) {
		this.valueArchive = valueArchive;
	}
}

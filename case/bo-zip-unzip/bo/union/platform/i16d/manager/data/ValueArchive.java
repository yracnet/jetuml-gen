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

import java.util.Date;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import bo.union.police.PoliceBase;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class ValueArchive extends PoliceBase implements ValueItem, Serializable {

	private static final long serialVersionUID = 1L;
	@XmlElement
	private Integer attemptNro;
	@XmlElement
	private String attemptState;
	@XmlElement(type = Date.class)
	private Date dateFrom;
	@XmlElement(type = Date.class)
	private Date dateTo;
	@XmlElement
	private ArchiveType archiveType;
	@XmlElement
	private Value value;
	@XmlElement
	private Long valueArchiveId;
	@XmlElement
	private Content content;
	@XmlElement
	private List<Error> errorList;

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	public Integer getAttemptNro() {
		return attemptNro;
	}

	public void setAttemptNro(Integer value) {
		attemptNro = value;
	}

	public String getAttemptState() {
		return attemptState;
	}

	public void setAttemptState(String value) {
		attemptState = value;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date value) {
		dateFrom = value;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date value) {
		dateTo = value;
	}

	public ArchiveType getArchiveType() {
		return archiveType;
	}

	public void setArchiveType(ArchiveType archiveType) {
		this.archiveType = archiveType;
	}

	@Override
	public Value getValue() {
		return value;
	}

	@Override
	public void setValue(Value value) {
		this.value = value;
	}

	public Long getValueArchiveId() {
		return valueArchiveId;
	}

	public void setValueArchiveId(Long value) {
		valueArchiveId = value;
	}

	public List<Error> getErrorList() {
		return errorList;
	}

	public void setErrorList(List<Error> errorList) {
		this.errorList = errorList;
	}
}

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

import bo.union.comp.FileValue;
import java.util.Date;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import bo.union.police.PoliceBase;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class ValueCertificate extends PoliceBase implements ValueItem, Serializable {

	private static final long serialVersionUID = 1L;
	@XmlElement
	private String type;
	@XmlElement
	private boolean newArchive;
	@XmlElement
	private FileValue archive;
	@XmlElement
	private PasswordValue password;
	@XmlElement
	private String summary;
	@XmlElement
	private String nameSignature;
	@XmlElement
	private String algSignture;
	@XmlElement
	private String algDigest;
	@XmlElement
	private String algEncrypt;
	@XmlElement(type = Date.class)
	private Date dateFrom;
	@XmlElement(type = Date.class)
	private Date dateTo;
	@XmlElement
	private Value value;
	@XmlElement
	private Long valueCertificateId;

	public String getType() {
		return type;
	}

	public void setType(String value) {
		this.type = value;
	}

	public byte[] getArchiveContent() {
		return archive == null ? null : archive.getContent();
	}

	public boolean isNewArchive() {
		return newArchive;
	}

	public void setNewArchive(boolean newArchive) {
		this.newArchive = newArchive;
	}

	public FileValue getArchive() {
		return archive;
	}

	public void setArchive(FileValue value) {
		this.archive = value;
	}

	public PasswordValue getPassword() {
		return password;
	}

	public void setPassword(PasswordValue value) {
		this.password = value;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getNameSignature() {
		return nameSignature;
	}

	public void setNameSignature(String value) {
		this.nameSignature = value;
	}

	public String getAlgSignture() {
		return algSignture;
	}

	public void setAlgSignture(String value) {
		this.algSignture = value;
	}

	public String getAlgDigest() {
		return algDigest;
	}

	public void setAlgDigest(String algDigest) {
		this.algDigest = algDigest;
	}

	public String getAlgEncrypt() {
		return algEncrypt;
	}

	public void setAlgEncrypt(String value) {
		this.algEncrypt = value;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date value) {
		this.dateFrom = value;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date value) {
		this.dateTo = value;
	}

	@Override
	public Value getValue() {
		return value;
	}

	@Override
	public void setValue(Value value) {
		this.value = value;
	}

	public Long getValueCertificateId() {
		return valueCertificateId;
	}

	public void setValueCertificateId(Long value) {
		this.valueCertificateId = value;
	}

	public boolean isPublic() {
		return "PUBL".equals(type);
	}

	public boolean isPrivate() {
		return "PRIV".equals(type);
	}
}

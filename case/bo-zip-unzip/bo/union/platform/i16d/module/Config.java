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
package bo.union.platform.i16d.module;

import java.nio.charset.Charset;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author wyujra
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Config {

	@XmlElement
	private String mode;
	@XmlElement
	private String xml;
	@XmlElement
	private String name;
	@XmlElement
	private String xpath;

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public byte[] getXmlBytes(Charset charset) {
		return xml == null ? new byte[0] : xml.getBytes(charset);
	}

	public byte[] getXmlBytes() {
		return xml == null ? new byte[0] : xml.getBytes();
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getXpath() {
		return xpath;
	}

	public void setXpath(String xpath) {
		this.xpath = xpath;
	}

	public boolean isModeSignXml() {
		return "SIGN-XML".equals(mode);
	}

	public boolean isModeSignXmlXPath() {
		return "SIGN-XML-XPATH".equals(mode);
	}

	public boolean isModeSignObj() {
		return "SIGN-OBJ".equals(mode);
	}

	public boolean isModeVerify() {
		return "VERIFY-XML".equals(mode);
	}

	public boolean isModeVerifyXPath() {
		return "VERIFY-XML-XPATH".equals(mode);
	}

	@Override
	public String toString() {
		return "Config{" + name + "," + mode + "," + xpath + "," + (xml == null ? 0 : xml.length()) + "}";
	}
}

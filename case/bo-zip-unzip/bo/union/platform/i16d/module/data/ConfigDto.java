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
package bo.union.platform.i16d.module.data;

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
public class ConfigDto {

	@XmlElement
	private String name;
	@XmlElement
	private String test;
	@XmlElement
	private String data;
	@XmlElement
	private String method;
	@XmlElement
	private String contentType;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}

	public byte[] getDataBytes(Charset charset) {
		return data == null ? new byte[0] : data.getBytes(charset);
	}

	public byte[] getDataBytes() {
		return data == null ? new byte[0] : data.getBytes();
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public boolean isTestData() {
		return "DATA".equals(test);
	}

	public boolean isTestNone() {
		return "NONE".equals(test);
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Override
	public String toString() {
		StringBuilder out = new StringBuilder(getClass().getSimpleName());
		out.append("\n{");
		out.append("\n\tname:").append(name);
		out.append("\n\tmethod:").append(method);
		out.append("\n\tcontentType:").append(contentType);
		out.append("\n}");
		return out.toString();
	}
}

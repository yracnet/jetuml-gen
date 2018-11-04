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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.union.platform.i16d.util;

import bo.union.i16d.data.SendFeature;
import bo.union.platform.i16d.runtime.entity.I16dUrlView;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import bo.union.i16d.data.GatewayFeature;

/**
 *
 * @author wyujra
 */
public class SenderConfig {

	private String urlRemote;
	private String contentType = "application/json";
	private String method = "POST";
	private int readTimeout = 100;
	private int connectTimeout = 100;

	public SenderConfig() {
	}

	public SenderConfig(GatewayFeature configGateway, I16dUrlView urlConfig) {
		SendFeature configHttp = (SendFeature) configGateway;
		urlRemote = urlConfig.getUrlRemote();
		// "http://suma.aduana.gob.bo/b-param/rest/tipoOperador";
		// "http://suma.aduana.gob.bo/b-sso/rest/captcha";
		// "https://www.oopp.gob.bo/ajax/ecobolCorrespondenciaPorNombreDepartamento/COCHABAMBA";
		contentType = configHttp.getContentType();
		method = configHttp.getMethod();
		connectTimeout = urlConfig.getConnectTimeout();
		readTimeout = urlConfig.getReadTimeout();
	}

	public String getUrlRemote() {
		return urlRemote;
	}

	public void setUrlRemote(String urlRemote) {
		this.urlRemote = urlRemote;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public int getReadTimeout() {
		return readTimeout;
	}

	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public URL createURL() throws MalformedURLException {
		return new URL(urlRemote);
	}

	public HttpURLConnection createURLConnection(boolean senderData) throws MalformedURLException, IOException {
		URL local = createURL();
		HttpURLConnection con = (HttpURLConnection) local.openConnection();
		con.setDoInput(true);
		con.setDoOutput(senderData);
		con.setConnectTimeout(connectTimeout);
		con.setReadTimeout(readTimeout);
		con.setUseCaches(false);
		con.setDefaultUseCaches(false);
		con.setRequestMethod(method);
		con.setRequestProperty("User-Agent", "I16D Gateway");
		con.setRequestProperty("Content-Type", contentType);
		con.setRequestProperty("Accept", contentType + ",text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		return con;
	}
}

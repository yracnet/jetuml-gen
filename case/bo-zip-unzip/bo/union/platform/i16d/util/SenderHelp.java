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

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;

/**
 *
 * @author sipaco
 */
public class SenderHelp {

	public static void main(String[] arg) {
		System.getProperties().put("proxySet", "true");
		System.getProperties().put("proxyHost", "192.168.232.117");
		System.getProperties().put("proxyPort", "3128");
		System.getProperties().put("sun.net.spi.nameservice.provider.1", "dns,sun");
		System.getProperties().put("sun.net.spi.nameservice.nameservers", "192.168.232.19,192.168.232.18");
		try {
			SenderConfig config = new SenderConfig();
			config.setUrlRemote("http://suma.aduana.gob.bo/b-param/rest/tipoOperador");
			// config.setUrlRemote("http://suma.aduana.gob.bo/b-sso/rest/captcha");
			// config.setUrlRemote("https://www.oopp.gob.bo/ajax/ecobolCorrespondenciaPorNombreDepartamento/COCHABAMBA");
			config.setContentType("application/json");
			config.setMethod("GET");
			config.setReadTimeout(5000);
			byte data[] = null;// "sss".getBytes();
			byte resp[] = SenderHelp.send(config, data);
			String str = resp == null ? null : new String(resp);
			System.out.println("--->" + resp + " - " + str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static byte[] from(InputStream is) throws IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		int nRead;
		byte[] data = new byte[16384];
		while ((nRead = is.read(data, 0, data.length)) != -1) {
			buffer.write(data, 0, nRead);
		}
		buffer.flush();
		return buffer.toByteArray();
	}

	public static byte[] send(SenderConfig config, byte[] data) throws MalformedURLException, ProtocolException, IOException {
		HttpURLConnection con = null;
		try {
			con = config.createURLConnection(data != null);
			if (data != null) {
				try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
					wr.write(data);
					wr.flush();
				}
			}
			return from(con.getInputStream());
		} finally {
			if (con != null) {
				con.disconnect();
			}
		}
	}
}

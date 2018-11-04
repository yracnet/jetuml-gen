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
package bo.union.platform.i16d.manager.local;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Base64;
import java.util.Date;
import java.util.regex.Pattern;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sipaco
 */
public class HelpLocal {

	private static final String REGEX_URL = "^((((https?|ftps?|gopher|telnet|nntp)://)|(mailto:|news:))"
			+ "(%[0-9A-Fa-f]{2}|[-()_.!~*';/?:@&=+$,A-Za-z0-9])+)" + "([).!';/?:,][[:blank:]])?$";
	public static final Pattern URL_PATTERN = Pattern.compile(REGEX_URL);
	public static final Pattern REGEX_USERNAME = Pattern.compile("[A-Z0-9]*");
	public static final Pattern REGEX_NAME = Pattern.compile("[A-Z0-9_]*");
	public static final Pattern REGEX_NAME_FILE = Pattern.compile("[A-Z0-9_\\s]*");
	public static final Pattern REGEX_T_C_NAME = Pattern.compile("[A-Z0-9_]*");
	public static final Pattern REGEX_T_C_NAME_ESP = Pattern.compile("[A-Za-z0-9_]*");
	public static final Pattern REGEX_NAME_GROUPER = Pattern.compile("[A-Z0-9\\s]*");
	public static final Pattern REGEX_DESCRIPTION = Pattern.compile("[.A-Za-zÁÉÍÓÚÑáéíóúñ\\s]*");
	public static final int FILE_MIN_SIZE = 0;
	public static final int FILE_MAX_SIZE = 999;
	public static Cipher cipher;
	public static SecretKey secretKey;
	public static final String STATE_LOAD = "LOAD";
	public static final String STATE_ERROR = "ERR";

	private HelpLocal() {
		throw new IllegalStateException("Utility class");
	}

	public static Date currentDate() {
		return new Date();
	}

	public static int validateExistVigencia(EntityManager em, String typeValue, Long valueIdItem, Long valueId, Date fechaDesde, Date fechaHasta) {
		String where;
		if (valueIdItem == null) {
			where = "WHERE ";
		} else {
			where = "WHERE o.value[TYPE]Id <> :valueTypeId AND ";
		}
		String sql = "SELECT COUNT(o) " + // COUNT
				"FROM I16dValue[TYPE] o " + // TABLE TYPE
				where + // ID TYPE
				"o.i16dValue.valueId = :valueId " + // ID VALUE
				"AND o.i16dValue.state = 'A' " + // ACTIVE
				"AND ( " + // RANGE
				":fechaDesde BETWEEN o.dateFrom AND o.dateTo" + // FECHA_DESDE
				" OR " + // OR
				":fechaHasta BETWEEN o.dateFrom AND o.dateTo" + // FECHA_HASTA
				" OR " + // OR
				" (:fechaDesde < o.dateFrom AND :fechaHasta > o.dateTo)" + // OR
				")";
		sql = sql.replace("[TYPE]", typeValue);
		Query q = em.createQuery(sql);
		if (valueIdItem != null) {
			q.setParameter("valueTypeId", valueIdItem);
		}
		q.setParameter("valueId", valueId);
		q.setParameter("fechaDesde", fechaDesde);
		q.setParameter("fechaHasta", fechaHasta);
		Number count = (Number) q.getSingleResult();
		return count.intValue();
	}

	public static Long findSameCabecera(EntityManager em, String typeValue, Long valueId) {
		String sql = "SELECT COUNT(o) " + // COUNT
				"FROM I16dValue[TYPE] o " + // TYPE TABLE
				"WHERE o.i16dValue.valueId = :valueId"; // ID VALUE
		sql = sql.replace("[TYPE]", typeValue);
		Query q = em.createQuery(sql);
		q.setParameter("valueId", valueId);
		return (Long) q.getSingleResult();
	}

	public static void initEncrypt() throws Exception {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		keyGenerator.init(128); // block size is 128bits
		secretKey = keyGenerator.generateKey();
		cipher = Cipher.getInstance("AES"); // SunJCE provider AES algorithm, mode(optional) and padding schema(optional)
	}

	public static String encrypt(String plainText, SecretKey secretKey) throws Exception {
		byte[] plainTextByte = plainText.getBytes();
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		byte[] encryptedByte = cipher.doFinal(plainTextByte);
		Base64.Encoder encoder = Base64.getEncoder();
		String encryptedText = encoder.encodeToString(encryptedByte);
		return encryptedText;
	}

	public static String decrypt(String encryptedText, SecretKey secretKey) throws Exception {
		Base64.Decoder decoder = Base64.getDecoder();
		byte[] encryptedTextByte = decoder.decode(encryptedText);
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		byte[] decryptedByte = cipher.doFinal(encryptedTextByte);
		String decryptedText = new String(decryptedByte);
		return decryptedText;
	}

	public static Date convertToDate(String input, String format) {
		Date date = null;
		try {
			DateFormat formatter;
			formatter = new SimpleDateFormat(format);
			date = formatter.parse(input);
		} catch (ParseException ex) {
			Logger.getLogger(HelpLocal.class.getName()).log(Level.SEVERE, null, ex);
		}
		return date;
	}

	public static Date convertFormatDate(Date input, String format) {
		Date dt = null;
		try {
			SimpleDateFormat sm = new SimpleDateFormat(format);
			String strDate = sm.format(input);
			dt = sm.parse(strDate);
		} catch (ParseException ex) {
			Logger.getLogger(HelpLocal.class.getName()).log(Level.SEVERE, null, ex);
		}
		return dt;
	}
}

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

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author sipaco
 */
public class SendMail {

	static final String FROM = "sipaco@bancounion.com.bo";
	static final String FROMNAME = "Sender Name";
	static final String SMTP_USERNAME = "bancounion\\sipaco";
	static final String SMTP_PASSWORD = "avalery$1212";
	static final String CONFIGSET = "ConfigSet";
	static final String HOST = "127.0.0.1";
	static final int PORT = 1025;
	static final String SUBJECT = "Carga de Archivo";
	static String BODY = "Se carg&oacute el archivo ";

	public static void sendNotification(List<String> mail, String textBody) {
		try {
			InternetAddress[] addressTo = new InternetAddress[mail.size()];
			for (int i = 0; i < mail.size(); i++) {
				addressTo[i] = new InternetAddress(mail.get(i));
			}
			// Create a Properties object to contain connection configuration information.
			Properties props = System.getProperties();
			props.put("mail.transport.protocol", "smtp");
			props.put("mail.smtp.port", PORT);
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.auth", "true");
			// Create a Session object to represent a mail session with the specified properties.
			Session session = Session.getDefaultInstance(props);
			// Create a message with the specified information.
			MimeMessage msg = new MimeMessage(session);
			try {
				msg.setFrom(new InternetAddress(FROM, FROMNAME));
			} catch (UnsupportedEncodingException ex) {
				Logger.getLogger(SendMail.class.getName()).log(Level.SEVERE, null, ex);
			}
			// msg.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));
			msg.setRecipients(Message.RecipientType.TO, addressTo);
			msg.setSubject(SUBJECT);
			msg.setContent(BODY + textBody, "text/html");
			// Add a configuration set header. Comment or delete the
			// next line if you are not using a configuration set
			msg.setHeader("X-SES-CONFIGURATION-SET", CONFIGSET);
			// Create a transport.
			Transport transport = session.getTransport();
			// Send the message.
			try {
				// Connect to Amazon SES using the SMTP username and password you specified above.
				transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
				// Send the email.
				transport.sendMessage(msg, msg.getAllRecipients());
			} catch (Exception ex) {
			} finally {
				// Close and terminate the connection.
				transport.close();
			}
		} catch (MessagingException ex) {
			Logger.getLogger(SendMail.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}

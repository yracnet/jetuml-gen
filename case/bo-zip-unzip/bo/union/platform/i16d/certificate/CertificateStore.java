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
package bo.union.platform.i16d.certificate;

import bo.union.lang.ServiceException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wyujra
 */
public class CertificateStore {

	private static final Logger LOGGER = Logger.getLogger(CertificateStore.class.getName());

	public static Object getCryptoInstance(byte[] bytes, char[] passphrase) throws ServiceException {
		boolean isPrivate = passphrase != null;
		boolean isPublic = passphrase == null;
		LOGGER.log(Level.INFO, "FILE SIZE: {0}", bytes.length);
		if (isPrivate) {
			try {
				KeyStore keyStore = KeyStore.getInstance("pkcs12");
				ByteArrayInputStream keystoreStream = new ByteArrayInputStream(bytes);
				keyStore.load(keystoreStream, passphrase);
				String alias = keyStore.aliases().nextElement();
				return (KeyStore.PrivateKeyEntry) keyStore.getEntry(alias, new KeyStore.PasswordProtection(passphrase));
			} catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException e) {
				throw new ServiceException("Error Interno: " + e.getLocalizedMessage(), e);
			} catch (UnrecoverableEntryException e) {
				throw new ServiceException("No se puede acceder al content del certificate privado. Verifique el password y la integridad del archive", e);
			}
		} else if (isPublic) {
			try {
				CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
				ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
				return (X509Certificate) certFactory.generateCertificate(stream);
			} catch (CertificateException e) {
				throw new ServiceException("Error Interno: " + e.getLocalizedMessage(), e);
			}
		} else {
			throw new ServiceException("El type documento no definido!");
		}
	}

	public static String validateCertificate(byte[] bytes, char[] passphrase) throws ServiceException {
		X509Certificate cert;
		Object object = getCryptoInstance(bytes, passphrase);
		if (object instanceof KeyStore.PrivateKeyEntry) {
			KeyStore.PrivateKeyEntry pk = (KeyStore.PrivateKeyEntry) object;
			cert = (X509Certificate) pk.getCertificate();
		} else {
			cert = (X509Certificate) object;
		}
		if (cert != null) {
			return cert.getSubjectDN() + " - " + cert.getSigAlgName();
		} else {
			return "Certificate at " + new Date();
		}
	}
}

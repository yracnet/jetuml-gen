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

import bo.union.lang.ValidationException;
import java.io.ByteArrayInputStream;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class CertificateValidator {

	private final byte[] certificate;
	private final String alias;

	public CertificateValidator(CertificateConfig cer) {
		certificate = cer.getCertificateFile();
		alias = cer.getKeyNameSignature();
	}

	public X509Certificate getX509Certificate() throws ValidationException {
		try {
			CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
			ByteArrayInputStream stream = new ByteArrayInputStream(certificate);
			return (X509Certificate) certFactory.generateCertificate(stream);
		} catch (CertificateException e) {
			throw new ValidationException("No se pudo cargar el certificate publico: " + alias, e);
		}
	}

	public boolean validateElement(Element nodeSignature) throws ValidationException {
		Certificate certificate = getX509Certificate();
		PublicKey publicKey = certificate.getPublicKey();
		try {
			DOMValidateContext valContext = new DOMValidateContext(publicKey, nodeSignature);
			XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");
			XMLSignature signature = fac.unmarshalXMLSignature(valContext);
			return signature.validate(valContext);
		} catch (MarshalException e) {
			throw new ValidationException("MarshalException: No se valido la firma", e);
		} catch (XMLSignatureException e) {
			throw new ValidationException("XMLSignatureException: No se valido la firma", e);
		}
	}

	public void removeSignature(Element signature) throws ValidationException {
		if (signature == null) {
			throw new ValidationException("El nodo signature no es valido!", null);
		} else if (!XMLSignature.XMLNS.equals(signature.getNamespaceURI())) {
			throw new ValidationException("El NS del nodo no es valido!", null);
		}
		// document.compareDocumentPosition(signature);
		Node parent = signature.getParentNode();
		parent.removeChild(signature);
		NodeList nodeList = signature.getElementsByTagNameNS(XMLSignature.XMLNS, "Object");
		for (int i = 0; i < nodeList.getLength(); i++) {
			NodeList childList = nodeList.item(i).getChildNodes();
			for (int j = 0; j < childList.getLength(); j++) {
				parent.appendChild(childList.item(j));
			}
		}
	}
}

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
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Collections;
import static java.util.Collections.singletonList;
import java.util.List;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dom.DOMStructure;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLObject;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.KeyName;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class CertificateSigner {

	private final byte[] CERTIFICATE_FILE;
	private final String NAME;
	private final char[] PASSWORD;
	private final String KEY_NAME_VALUE;
	private final XMLSignatureFactory SIGN_FACTORY;
	private final List<XMLObject> xmlObjectList = new ArrayList<>();
	private KeyStore.PrivateKeyEntry privateKeyEntry = null;
	private final String ALG_DIGEST;
	private final String ALG_SIGNATURE;

	public CertificateSigner(CertificateConfig cer, char[] pass) {
		CERTIFICATE_FILE = cer.getCertificateFile();
		KEY_NAME_VALUE = cer.getKeyNameSignature();
		NAME = cer.getName();
		PASSWORD = pass;
		try {
			SIGN_FACTORY = XMLSignatureFactory.getInstance("DOM", "XMLDSig");
		} catch (NoSuchProviderException e) {
			throw new RuntimeException("Provedor XMLDSig NOEXISTE", e);
		}
		ALG_DIGEST = cer.getAlgDigestMethod();
		ALG_SIGNATURE = cer.getAlgSignatureMethod();
	}

	public void addXMLObject(String name, Node node) {
		XMLStructure content = new DOMStructure(node);
		XMLObject xmlObject = SIGN_FACTORY.newXMLObject(Collections.singletonList(content), name, null, null);
		xmlObjectList.add(xmlObject);
	}

	public XMLSignature createXMLSignature() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
		return createXMLSignature(createSignedInfo(), createKeyInfo());
	}

	public XMLSignature createXMLSignature(SignedInfo signedInfo, KeyInfo keyInfo) {
		if (xmlObjectList.isEmpty()) {
			return SIGN_FACTORY.newXMLSignature(signedInfo, keyInfo);
		} else {
			return SIGN_FACTORY.newXMLSignature(signedInfo, keyInfo, xmlObjectList, null, null);
		}
	}

	public SignedInfo createSignedInfo() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
		CanonicalizationMethod canonicalizationMethod = SIGN_FACTORY.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE,
				(C14NMethodParameterSpec) null);
		SignatureMethod signatureMethod = SIGN_FACTORY.newSignatureMethod(ALG_SIGNATURE, null);
		DigestMethod digestMethod = SIGN_FACTORY.newDigestMethod(ALG_DIGEST, null);
		Transform transform = SIGN_FACTORY.newTransform(Transform.ENVELOPED, (TransformParameterSpec) null);
		List<Transform> transformList = singletonList(transform);
		List<Reference> referenceList = new ArrayList<>();
		for (XMLObject xmlObject : xmlObjectList) {
			Reference reference = SIGN_FACTORY.newReference("#" + xmlObject.getId(), digestMethod, transformList, null, null);
			referenceList.add(reference);
		}
		if (referenceList.isEmpty()) {
			Reference reference = SIGN_FACTORY.newReference("", digestMethod, transformList, null, null);
			referenceList.add(reference);
		}
		return SIGN_FACTORY.newSignedInfo(canonicalizationMethod, signatureMethod, referenceList);
	}

	public KeyInfo createKeyInfo() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
		return createKeyInfo(KEY_NAME_VALUE);
	}

	public KeyInfo createKeyInfo(String keyNameValue) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
		KeyInfoFactory keyInfoFactory = SIGN_FACTORY.getKeyInfoFactory();
		KeyName keyName = keyInfoFactory.newKeyName(keyNameValue);
		return keyInfoFactory.newKeyInfo(singletonList(keyName));
	}

	public PrivateKey loadPrivateKey()
			throws NoSuchAlgorithmException, UnrecoverableEntryException, KeyStoreException, IOException, CertificateException {
		return getPrivateKeyEntry().getPrivateKey();
	}

	public KeyStore.PrivateKeyEntry getPrivateKeyEntry()
			throws NoSuchAlgorithmException, UnrecoverableEntryException, KeyStoreException, IOException, CertificateException {
		if (privateKeyEntry == null) {
			KeyStore keyStore = KeyStore.getInstance("pkcs12");
			InputStream keystoreStream = new ByteArrayInputStream(CERTIFICATE_FILE);
			keyStore.load(keystoreStream, PASSWORD);
			String aliasStore = keyStore.aliases().nextElement();
			privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(aliasStore, new KeyStore.PasswordProtection(PASSWORD));
		}
		return privateKeyEntry;
	}

	public PrivateKey getPrivateKey()
			throws NoSuchAlgorithmException, UnrecoverableEntryException, KeyStoreException, IOException, CertificateException {
		return getPrivateKeyEntry().getPrivateKey();
	}

	public DigestMethod getDigestMethod() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
		return SIGN_FACTORY.newDigestMethod(ALG_DIGEST, null);
	}

	public SignatureMethod getSignatureMethod() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
		return SIGN_FACTORY.newSignatureMethod(ALG_SIGNATURE, null);
	}

	public Document signObject(String name, Node node) throws ValidationException {
		try {
			if (node instanceof Document) {
				node = ((Document) node).getDocumentElement();
			}
			Document newDocument = CertificateHelp.createDocument();
			addXMLObject(name, node);
			XMLSignature signature = createXMLSignature();
			PrivateKey privateKey = loadPrivateKey();
			DOMSignContext dsc = new DOMSignContext(privateKey, newDocument);
			dsc.setDefaultNamespacePrefix("ds");
			signature.sign(dsc);
			return newDocument;
		} catch (XMLSignatureException ex) {
			throw new ValidationException("Error al FIRMAR el Documento", ex);
		} catch (MarshalException ex) {
			throw new ValidationException("Error al Procesar la Firma", ex);
		} catch (NoSuchAlgorithmException ex) {
			throw new ValidationException("Error, Algoritmo no reconocido", ex);
		} catch (UnrecoverableEntryException ex) {
			throw new ValidationException("Error, Entidad no identificada", ex);
		} catch (KeyStoreException ex) {
			throw new ValidationException("Error, Llave privada con errores", ex);
		} catch (IOException ex) {
			throw new ValidationException("Error de I/O", ex);
		} catch (CertificateException ex) {
			throw new ValidationException("Error, Cerificado no valido", ex);
		} catch (InvalidAlgorithmParameterException ex) {
			throw new ValidationException("Error, Parametros del argoritmo", ex);
		} catch (Exception ex) {
			throw new ValidationException("Error inesperado!", ex);
		}
	}

	public Document signDocument(Document node) throws ValidationException {
		try {
			XMLSignature signature = createXMLSignature();
			PrivateKey privateKey = loadPrivateKey();
			DOMSignContext dsc = new DOMSignContext(privateKey, node.getDocumentElement());
			dsc.setDefaultNamespacePrefix("ds");
			signature.sign(dsc);
			return node;
		} catch (XMLSignatureException ex) {
			throw new ValidationException("Error al FIRMAR el Documento", ex);
		} catch (MarshalException ex) {
			throw new ValidationException("Error al Procesar la Firma", ex);
		} catch (NoSuchAlgorithmException ex) {
			throw new ValidationException("Error, Algoritmo no reconocido", ex);
		} catch (UnrecoverableEntryException ex) {
			throw new ValidationException("Error, Entidad no identificada", ex);
		} catch (KeyStoreException ex) {
			throw new ValidationException("Error, Llave privada con errores", ex);
		} catch (IOException ex) {
			throw new ValidationException("Error de I/O", ex);
		} catch (CertificateException ex) {
			throw new ValidationException("Error, Cerificado no valido", ex);
		} catch (InvalidAlgorithmParameterException ex) {
			throw new ValidationException("Error, Parametros del argoritmo", ex);
		} catch (Exception ex) {
			throw new ValidationException("Error inesperado!", ex);
		}
	}

	public static void printDocument(Document document, OutputStream out) {
		try {
			TransformerFactory TRANSFORMER = TransformerFactory.newInstance();
			Transformer transformer = TRANSFORMER.newTransformer();
			transformer.transform(new DOMSource(document), new StreamResult(new OutputStreamWriter(out, "UTF-8")));
		} catch (UnsupportedEncodingException | TransformerException ex) {
			ex.printStackTrace();
		}
	}
}

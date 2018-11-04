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
import bo.union.lang.ValidationException;
import bo.union.platform.i16d.runtime.entity.I16dCertificateView;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author wyujra
 */
public class CertificateHelp {

	private static final XPathFactory XPATH_FACTORY = XPathFactory.newInstance();
	private static final DocumentBuilderFactory DOCUMENT_BUILD_FACTORY = DocumentBuilderFactory.newInstance();
	private static final TransformerFactory TRANSFORMER = TransformerFactory.newInstance();
	static {
		DOCUMENT_BUILD_FACTORY.setIgnoringComments(true);
		DOCUMENT_BUILD_FACTORY.setIgnoringElementContentWhitespace(true);
		DOCUMENT_BUILD_FACTORY.setValidating(false);
		DOCUMENT_BUILD_FACTORY.setNamespaceAware(true);
	}

	public static Document createDocument() throws ValidationException {
		try {
			DocumentBuilder db = DOCUMENT_BUILD_FACTORY.newDocumentBuilder();
			return db.newDocument();
		} catch (ParserConfigurationException | DOMException e) {
			throw new ValidationException("No se pudo clonar el documento", e);
		}
	}

	public static Document createDocument(Node node) throws ValidationException {
		try {
			DocumentBuilder builder = DOCUMENT_BUILD_FACTORY.newDocumentBuilder();
			Document newDocument = builder.newDocument();
			if (node instanceof Document) {
				node = ((Document) node).getDocumentElement();
			}
			Node importedNode = newDocument.importNode(node, true);
			newDocument.appendChild(importedNode);
			return newDocument;
		} catch (ParserConfigurationException | DOMException e) {
			throw new ValidationException("No se pudo crear el documento", e);
		}
	}

	public static NodeList findNodeByXPath(Document document, String xpathString) throws ValidationException {
		ValidationException validation = new ValidationException("XPath Process");
		validation.isNull(document, "XML Document");
		validation.isNull(xpathString, "XML Path");
		validation.throwException();
		try {
			XPath xpath = XPATH_FACTORY.newXPath();
			XPathExpression xpathExp = xpath.compile(xpathString);
			NodeList nodes = (NodeList) xpathExp.evaluate(document, XPathConstants.NODESET);
			validation.addWhen(nodes.getLength() == 0, "XML Path Not Found Elements");
			validation.throwException();
			return nodes;
		} catch (XPathExpressionException e) {
			throw new ValidationException("La expresion: " + xpathString + " No es valida!", e);
		}
	}

	public static void printDocument(Node node) throws ServiceException {
		try {
			Transformer transformer = TRANSFORMER.newTransformer();
			transformer.transform(new DOMSource(node), new StreamResult(new OutputStreamWriter(System.err, "UTF-8")));
		} catch (TransformerException | UnsupportedEncodingException ex) {
			throw ServiceException.assertException("Error al imprimir el nodo: " + node, ex);
		}
	}

	public static CertificateConfig certificateConfig(I16dCertificateView cert) {
		return new CertificateConfig() {

			@Override
			public byte[] getCertificateFile() {
				return cert.getArchive();
			}

			@Override
			public String getKeyNameSignature() {
				return cert.getKeyNameSignature();
			}

			@Override
			public String getName() {
				return cert.getName();
			}

			@Override
			public String getAlgDigestMethod() {
				return cert.getAlgDigestMethod();
			}

			@Override
			public String getAlgSignatureMethod() {
				return cert.getAlgSignatureMethod();
			}
		};
	}
}

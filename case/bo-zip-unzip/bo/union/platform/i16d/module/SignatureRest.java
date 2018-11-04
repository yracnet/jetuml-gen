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

import bo.union.i16d.SignatureProvider;
import bo.union.lang.ServiceException;
import bo.union.web.HTTPStatic;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Publicacion REST para la interface: ValueArchiveServ
 */
@Path("signature")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SignatureRest {

	private static final Logger LOGGER = Logger.getLogger(SignatureRest.class.getName());
	@EJB(name = SignatureProvider.NAME)
	private SignatureProvider serv;

	@GET
	@Path("info")
	public String info() throws ServiceException {
		return "Info Service: " + this + " by " + serv;
	}

	@POST
	@Path("sign")
	public byte[] sign(Config config) throws ServiceException {
		byte[] xml = null;
		try {
			LOGGER.log(Level.INFO, "SIGN STR {0}", config);
			LOGGER.log(Level.INFO, "XML-IN {0}", config.getXml());
			xml = config.getXmlBytes();
			String name = config.getName();
			String xpath = config.getXpath();
			if (config.isModeSignXml()) {
				xml = serv.signNode(name, xml);
			} else if (config.isModeSignXmlXPath()) {
				xml = serv.signNode(name, xml, xpath);
			} else if (config.isModeSignObj()) {
				xml = serv.signObject(name, xml);
			} else if (config.isModeVerify()) {
				xml = serv.signVerify(name, xml);
			} else if (config.isModeVerifyXPath()) {
				xml = serv.signVerify(name, xml, xpath);
			} else {
				HTTPStatic.error("MODE NOT FOUND: " + config.getMode());
				xml = ("ERROR:" + config).getBytes();
			}
		} catch (Exception e) { // all error!
			HTTPStatic.error("Error config: " + config, e);
			xml = ("EXCEPTION: " + e).getBytes();
		}
		LOGGER.log(Level.INFO, "XML-OUT {0}", new String(xml));
		return xml;
	}
}

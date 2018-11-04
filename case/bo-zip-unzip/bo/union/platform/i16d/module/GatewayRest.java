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

import bo.union.platform.i16d.module.data.ConfigDto;
import bo.union.i16d.GatewayProvider;
import bo.union.i16d.data.SendFeature;
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
 * Publicacion REST para la interface: GatewayProvider
 */
@Path("gateway")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GatewayRest {

	private static final Logger LOGGER = Logger.getLogger(GatewayRest.class.getName());
	@EJB(name = GatewayProvider.NAME)
	private GatewayProvider serv;

	@GET
	@Path("info")
	public String info() throws ServiceException {
		return "Info Service: " + this + " by " + serv;
	}

	@POST
	@Path("send")
	public String send(ConfigDto configDto) throws ServiceException {
		LOGGER.log(Level.INFO, "SEND---->{0}", configDto);
		String response = null;
		try {
			SendFeature feature = new SendFeature();
			feature.setTargetName(configDto.getName());
			feature.setMethod(configDto.getMethod());
			feature.setContentType(configDto.getContentType());
			if (configDto.isTestData()) {
				String data = configDto.getData();
				response = serv.sendString(data, feature);
			} else if (configDto.isTestNone()) {
				response = serv.sendString(null, feature);
			}
		} catch (Exception e) { // all error!
			response = "Error url: " + configDto + " - Cause: " + e.getMessage();
			HTTPStatic.error("Error url: " + configDto, e);
			e.printStackTrace();
		}
		return response;
	}
}

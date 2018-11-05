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
package bo.union.platform.i16d.manager;

import bo.union.platform.i16d.manager.data.Grouper;
import bo.union.platform.i16d.manager.filter.GrouperFtr;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import bo.union.web.HTTPStatic;
import bo.union.lang.ServiceException;

/**
 * Publicacion REST para la interface: GrouperServ
 */
@Path("grouper")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GrouperRest {

	@EJB
	private GrouperServ serv;

	/**
	 * Creacion Grouper
	 *
	 * @param value
	 * @return
	 * @throws ServiceException
	 */
	@POST
	@Path("create")
	public Grouper createGrouper(Grouper value) throws ServiceException {
			serv.createGrouper(value);
			HTTPStatic.info("Se ha guardado el registro: " + value.getName());
			return value;
		
	}
}

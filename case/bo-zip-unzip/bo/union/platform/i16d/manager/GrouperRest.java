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
	 * Informacion de la instancia
	 *
	 * @return String informacion
	 */
	@GET
	@Path("info")
	public String info() {
		return "Info Service: " + this + " by " + serv;
	}

	/**
	 * Listado de Grouper's
	 *
	 * @param filter
	 * @return
	 * @throws ServiceException
	 */
	@POST
	@Path("filter")
	public List<Grouper> filterGrouper(GrouperFtr filter) throws ServiceException {
		return serv.filterGrouper(filter);
	}

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
		try {
			serv.createGrouper(value);
			HTTPStatic.info("Se ha guardado el registro: " + value.getName());
			return value;
		} catch (ServiceException e) {
			HTTPStatic.error("No se pudo guardar el registro: " + value);
			throw e;
		}
	}

	/**
	 * Actualizar Grouper
	 *
	 * @param value
	 * @return
	 * @throws ServiceException
	 */
	@POST
	@Path("update")
	public Grouper updateGrouper(Grouper value) throws ServiceException {
		try {
			serv.updateGrouper(value);
			HTTPStatic.info("Se ha actualizado el registro: " + value.getName());
			return value;
		} catch (ServiceException e) {
			HTTPStatic.error("No se pudo actualizar el registro: " + value);
			throw e;
		}
	}

	/**
	 * Eliminacion Grouper
	 *
	 * @param value
	 * @return
	 * @throws ServiceException
	 */
	@POST
	@Path("remove")
	public Grouper removeGrouper(Grouper value) throws ServiceException {
		try {
			serv.removeGrouper(value);
			HTTPStatic.info("Se ha eliminado el registro: " + value.getName());
			return value;
		} catch (ServiceException e) {
			HTTPStatic.error("No se pudo eliminar el registro: " + value);
			throw e;
		}
	}

	/**
	 * Listado de Grouper's hijos
	 *
	 * @param value
	 * @return
	 * @throws ServiceException
	 */
	@POST
	@Path("children")
	public List<Grouper> childrenGrouper(Grouper value) throws ServiceException {
		return serv.childrenGrouper(value, 10);
	}
}

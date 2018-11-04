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

import bo.union.platform.i16d.manager.data.ArchiveType;
import bo.union.platform.i16d.manager.filter.ArchiveTypeFtr;
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
 * Publicacion REST para la interface: ArchiveTypeServ
 */
@Path("archive/type")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ArchiveTypeRest {

	@EJB
	private ArchiveTypeServ serv;

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
	 * Listado de Archive Type's
	 *
	 * @param filter
	 * @return
	 * @throws ServiceException
	 */
	@POST
	@Path("filter")
	public List<ArchiveType> filterArchiveType(ArchiveTypeFtr filter) throws ServiceException {
		return serv.filterArchiveType(filter);
	}

	/**
	 * Creacion Archive Type
	 *
	 * @param value
	 * @return
	 * @throws ServiceException
	 */
	@POST
	@Path("create")
	public ArchiveType createArchiveType(ArchiveType value) throws ServiceException {
		try {
			serv.createArchiveType(value);
			HTTPStatic.info("Se ha guardado el registro: " + value.getArchiveTypeId());
			return value;
		} catch (ServiceException e) {
			HTTPStatic.error("No se pudo guardar el registro: " + value);
			throw e;
		}
	}

	/**
	 * Actualizar Archive Type
	 *
	 * @param value
	 * @return
	 * @throws ServiceException
	 */
	@POST
	@Path("update")
	public ArchiveType updateArchiveType(ArchiveType value) throws ServiceException {
		try {
			serv.updateArchiveType(value);
			HTTPStatic.info("Se ha actualizado el registro: " + value.getArchiveTypeId());
			return value;
		} catch (ServiceException e) {
			HTTPStatic.error("No se pudo actualizar el registro: " + value);
			throw e;
		}
	}

	/**
	 * Eliminacion Archive Type
	 *
	 * @param value
	 * @return
	 * @throws ServiceException
	 */
	@POST
	@Path("remove")
	public ArchiveType removeArchiveType(ArchiveType value) throws ServiceException {
		try {
			serv.removeArchiveType(value);
			HTTPStatic.info("Se ha eliminado el registro: " + value.getArchiveTypeId());
			return value;
		} catch (ServiceException e) {
			HTTPStatic.error("No se pudo eliminar el registro: " + value);
			throw e;
		}
	}
}

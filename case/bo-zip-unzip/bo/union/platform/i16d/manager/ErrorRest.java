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

import bo.union.platform.i16d.manager.data.Error;
import bo.union.platform.i16d.manager.filter.ErrorFtr;
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
import bo.union.platform.i16d.manager.data.ValueArchive;

@Path("error")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ErrorRest {

	@EJB
	private ErrorServ serv;

	@GET
	@Path("info")
	public String info() {
		return "Info Service: " + this + " by " + serv;
	}

	@POST
	@Path("filter")
	public List<Error> filterError(ErrorFtr filter) throws ServiceException {
		return serv.filterError(filter);
	}

	@POST
	@Path("list")
	public List<Error> listError(ValueArchive value) throws ServiceException {
		return serv.listError(value);
	}

	@POST
	@Path("create")
	public Error createError(Error value) throws ServiceException {
		try {
			Error result = serv.createError(value);
			HTTPStatic.info("Se ha guardado el registro: " + result.getErrorId());
			return result;
		} catch (ServiceException e) {
			HTTPStatic.error("No se pudo guardar el registro: " + value);
			throw e;
		}
	}

	@POST
	@Path("update")
	public Error updateError(Error value) throws ServiceException {
		try {
			Error result = serv.updateError(value);
			HTTPStatic.info("Se ha actualizado el registro: " + result.getErrorId());
			return result;
		} catch (ServiceException e) {
			HTTPStatic.error("No se pudo actualizar el registro: " + value);
			throw e;
		}
	}

	@POST
	@Path("remove")
	public Error removeError(Error value) throws ServiceException {
		try {
			Error result = serv.removeError(value);
			HTTPStatic.info("Se ha eliminado el registro: " + result.getErrorId());
			return result;
		} catch (ServiceException e) {
			HTTPStatic.error("No se pudo eliminar el registro: " + value);
			throw e;
		}
	}
}
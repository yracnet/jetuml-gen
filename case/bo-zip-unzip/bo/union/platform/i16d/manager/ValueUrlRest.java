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

import bo.union.platform.i16d.manager.data.ValueUrl;
import bo.union.platform.i16d.manager.filter.ValueUrlFtr;
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
 *
 * @author sipaco
 */
@Path("value/url")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ValueUrlRest {

	@EJB
	private ValueUrlServ serv;

	/**
	 * Informacion de la instancia
	 * 
	 * @return String informacion
	 * @throws ServiceException
	 */
	@GET
	@Path("info")
	public String info() {
		return "Info Service: " + this + " by " + serv;
	}

	/**
	 * Listado de Url
	 * 
	 * @param filter
	 * @return
	 * @throws ServiceException
	 */
	@POST
	@Path("filter")
	public List<ValueUrl> filterValueUrl(ValueUrlFtr filter) throws ServiceException {
		return serv.filterValueUrl(filter);
	}

	/**
	 * Creacion Url
	 * 
	 * @param value
	 * @return
	 * @throws ServiceException
	 */
	@POST
	@Path("create")
	public ValueUrl createValueUrl(ValueUrl value) throws ServiceException {
		try {
			ValueUrl result = serv.createValueUrl(value);
			HTTPStatic.info("Se ha guardado el registro: " + result.getValueUrlId());
			return result;
		} catch (ServiceException e) {
			HTTPStatic.error("No se pudo guardar el registro: " + value);
			throw e;
		}
	}

	/**
	 * Actualizacion Url
	 * 
	 * @param value
	 * @return
	 * @throws ServiceException
	 */
	@POST
	@Path("update")
	public ValueUrl updateValueUrl(ValueUrl value) throws ServiceException {
		try {
			ValueUrl result = serv.updateValueUrl(value);
			HTTPStatic.info("Se ha actualizado el registro: " + result.getValueUrlId());
			return result;
		} catch (ServiceException e) {
			HTTPStatic.error("No se pudo actualizar el registro: " + value);
			throw e;
		}
	}

	/**
	 * Actualizacion Valor
	 * 
	 * @param value
	 * @return
	 * @throws ServiceException
	 */
	@POST
	@Path("update/value")
	public ValueUrl updateValue(ValueUrl value) throws ServiceException {
		try {
			serv.updateValue(value);
			HTTPStatic.info("Se ha actualizado el registro: " + value.getValue().getValueId());
			return value;
		} catch (ServiceException e) {
			HTTPStatic.error("No se pudo actualizar el registro: " + value);
			throw e;
		}
	}

	/**
	 * Eliminacion Url
	 * 
	 * @param value
	 * @return
	 * @throws ServiceException
	 */
	@POST
	@Path("remove")
	public ValueUrl removeValueUrl(ValueUrl value) throws ServiceException {
		try {
			ValueUrl result = serv.removeValueUrl(value);
			HTTPStatic.info("Se ha eliminado el registro: " + result.getValueUrlId());
			return result;
		} catch (ServiceException e) {
			HTTPStatic.error("No se pudo eliminar el registro: " + value);
			throw e;
		}
	}
}

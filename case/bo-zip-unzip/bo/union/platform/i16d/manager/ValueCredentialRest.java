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

import bo.union.platform.i16d.manager.data.ValueCredential;
import bo.union.platform.i16d.manager.filter.ValueCredentialFtr;
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
 * Publicacion REST para la interface: ValueCredentialServ
 */
@Path("value/credential")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ValueCredentialRest {

	@EJB
	private ValueCredentialServ serv;

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
	 * Listado de Value Credential's
	 *
	 * @param filter
	 * @return
	 * @throws ServiceException
	 */
	@POST
	@Path("filter")
	public List<ValueCredential> filterValueCredential(ValueCredentialFtr filter) throws ServiceException {
		return serv.filterValueCredential(filter);
	}

	/**
	 * Creacion Value Credential
	 *
	 * @param value
	 * @return
	 * @throws ServiceException
	 */
	@POST
	@Path("create")
	public ValueCredential createValueCredential(ValueCredential value) throws ServiceException {
		try {
			ValueCredential result = serv.createValueCredential(value);
			HTTPStatic.info("Se ha guardado el registro: " + result.getValueCredentialId());
			return result;
		} catch (ServiceException e) {
			HTTPStatic.error("No se pudo guardar el registro: " + value);
			throw e;
		}
	}

	/**
	 * Actualizar Value Credential
	 *
	 * @param value
	 * @return
	 * @throws ServiceException
	 */
	@POST
	@Path("update")
	public ValueCredential updateValueCredential(ValueCredential value) throws ServiceException {
		try {
			serv.updateValueCredential(value);
			HTTPStatic.info("Se ha actualizado el registro: " + value.getValueCredentialId());
			return value;
		} catch (ServiceException e) {
			HTTPStatic.error("No se pudo actualizar el registro: " + value);
			throw e;
		}
	}

	/**
	 * Actualizar Value Credential
	 *
	 * @param value
	 * @return
	 * @throws ServiceException
	 */
	@POST
	@Path("update/value")
	public ValueCredential updateValue(ValueCredential value) throws ServiceException {
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
	 * Eliminacion Value Credential
	 *
	 * @param value
	 * @return
	 * @throws ServiceException
	 */
	@POST
	@Path("remove")
	public ValueCredential removeValueCredential(ValueCredential value) throws ServiceException {
		try {
			serv.removeValueCredential(value);
			HTTPStatic.info("Se ha eliminado el registro: " + value.getValueCredentialId());
			return value;
		} catch (ServiceException e) {
			HTTPStatic.error("No se pudo eliminar el registro: " + value);
			throw e;
		}
	}
}

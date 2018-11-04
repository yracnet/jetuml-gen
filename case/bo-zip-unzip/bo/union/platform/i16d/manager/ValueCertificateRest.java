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

import bo.union.platform.i16d.manager.data.ValueCertificate;
import bo.union.platform.i16d.manager.filter.ValueCertificateFtr;
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
 * Publicacion REST para la interface: ValueCertificateServ
 */
@Path("value/certificate")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ValueCertificateRest {

	@EJB
	private ValueCertificateServ serv;

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
	 * Listado de Value Certificate's
	 *
	 * @param filter
	 * @return
	 * @throws ServiceException
	 */
	@POST
	@Path("filter")
	public List<ValueCertificate> filterValueCertificate(ValueCertificateFtr filter) throws ServiceException {
		return serv.filterValueCertificate(filter);
	}

	/**
	 * Creacion Value Certificate
	 *
	 * @param value
	 * @return
	 * @throws ServiceException
	 */
	@POST
	@Path("create")
	public ValueCertificate createValueCertificate(ValueCertificate value) throws ServiceException {
		try {
			serv.createValueCertificate(value);
			HTTPStatic.info("Se ha guardado el registro: " + value.getValueCertificateId());
			return value;
		} catch (ServiceException e) {
			HTTPStatic.error("No se pudo guardar el registro: " + value);
			throw e;
		}
	}

	/**
	 * Actualizar Value Certificate
	 *
	 * @param value
	 * @return
	 * @throws ServiceException
	 */
	@POST
	@Path("update")
	public ValueCertificate updateValueCertificate(ValueCertificate value) throws ServiceException {
		try {
			serv.updateValueCertificate(value);
			HTTPStatic.info("Se ha actualizado el registro: " + value.getValueCertificateId());
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
	public ValueCertificate updateValue(ValueCertificate value) throws ServiceException {
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
	 * Eliminacion Value Certificate
	 *
	 * @param value
	 * @return
	 * @throws ServiceException
	 */
	@POST
	@Path("remove")
	public ValueCertificate removeValueCertificate(ValueCertificate value) throws ServiceException {
		try {
			serv.removeValueCertificate(value);
			HTTPStatic.info("Se ha eliminado el registro: " + value.getValueCertificateId());
			return value;
		} catch (ServiceException e) {
			HTTPStatic.error("No se pudo eliminar el registro: " + value);
			throw e;
		}
	}
}

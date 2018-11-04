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

import bo.union.platform.i16d.manager.data.Deployment;
import bo.union.platform.i16d.manager.data.Version;
import bo.union.platform.i16d.manager.filter.DeploymentFtr;
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
 * Publicacion REST para la interface: DeploymentServ
 */
@Path("deployment")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DeploymentRest {

	@EJB
	private DeploymentServ serv;

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
	 * Listado de Deployment's
	 *
	 * @param filter
	 * @return
	 * @throws ServiceException
	 */
	@POST
	@Path("filter")
	public List<Deployment> filterDeployment(DeploymentFtr filter) throws ServiceException {
		return serv.filterDeployment(filter);
	}

	/**
	 * Listado de Version's
	 *
	 * @param filter
	 * @return
	 * @throws ServiceException
	 */
	@POST
	@Path("version/filter")
	public List<Version> filterVersion(Deployment filter) throws ServiceException {
		return serv.filterVersion(filter);
	}

	/**
	 * Actualizar Deployment
	 *
	 * @param value
	 * @return
	 * @throws ServiceException
	 */
	@POST
	@Path("update")
	public Deployment updateDeployment(Deployment value) throws ServiceException {
		try {
			Deployment result = serv.updateDeployment(value);
			HTTPStatic.info("Se ha actualizado el registro: " + result.getDeploymentId());
			return result;
		} catch (ServiceException e) {
			HTTPStatic.error("No se pudo actualizar el registro: " + value);
			throw e;
		}
	}
}

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

import bo.union.platform.i16d.manager.data.Bean;
import bo.union.platform.i16d.manager.data.BeanMethod;
import bo.union.platform.i16d.manager.filter.BeanFtr;
import bo.union.platform.i16d.manager.filter.BeanMethodFtr;
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
import bo.union.platform.i16d.manager.data.Deployment;
import bo.union.platform.i16d.manager.data.Privilege;
import bo.union.platform.i16d.provider.wrapper.ProviderWrap;

/**
 * Publicacion REST para la interface: BeanServ
 */
@Path("deployment/bean")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BeanRest {

	@EJB
	private BeanServ serv;
	@EJB
	private PrivilegeServ servPrivilege;

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
	 * Listado de Bean's
	 *
	 * @param filter
	 * @return
	 * @throws ServiceException
	 */
	@POST
	@Path("filter")
	public List<Bean> filterBean(BeanFtr filter) throws ServiceException {
		return serv.filterBean(filter);
	}

	/**
	 * Listado de Bean Method's
	 *
	 * @param filter
	 * @return
	 * @throws ServiceException
	 */
	@POST
	@Path("method/filter")
	public List<BeanMethod> filterBeanMethod(BeanMethodFtr filter) throws ServiceException {
		return serv.filterBeanMethod(filter);
	}

	/**
	 * Actualizar Bean Method
	 *
	 * @param value
	 * @return
	 * @throws ServiceException
	 */
	@POST
	@Path("method/update")
	public BeanMethod updateBeanMethod(BeanMethod value) throws ServiceException {
		try {
			BeanMethod result = serv.updateBeanMethod(value);
			HTTPStatic.info("Se ha actualizado el registro: " + result.getBeanMethodId());
			return result;
		} catch (ServiceException e) {
			HTTPStatic.error("No se pudo actualizar el registro: " + value);
			throw e;
		}
	}

	@POST
	@Path("module/filter")
	public List<Privilege> filterModule(ProviderWrap wrap) throws ServiceException {
		return servPrivilege.filterPrivilege(wrap.getBean(), wrap.getPrivilegeFtr());
	}

	@POST
	@Path("module/get")
	public List<Deployment> getModule(Deployment value) throws ServiceException {
		return serv.getModule();
	}

	@POST
	@Path("module/add")
	public Privilege addModule(Privilege value) throws ServiceException {
		try {
			servPrivilege.createPrivilege(value);
			HTTPStatic.info("Se ha adicionado el modulo: " + value.getDeployment().getName());
			return value;
		} catch (ServiceException e) {
			HTTPStatic.error("No se pudo adicionar el modulo: " + value);
			throw e;
		}
	}

	@POST
	@Path("module/remove")
	public Privilege removeModule(Privilege value) throws ServiceException {
		try {
			servPrivilege.removePrivilege(value);
			HTTPStatic.info("Se ha eliminado el modulo: " + value.getDeployment().getDeploymentId());
			return value;
		} catch (ServiceException e) {
			HTTPStatic.error("No se pudo eliminar el modulo: " + value);
			throw e;
		}
	}
}

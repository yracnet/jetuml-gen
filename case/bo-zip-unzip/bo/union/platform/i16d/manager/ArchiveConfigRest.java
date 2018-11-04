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

import bo.union.platform.i16d.manager.data.ArchiveConfig;
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
import bo.union.platform.i16d.manager.data.ArchiveType;
import bo.union.platform.i16d.manager.data.ConfigurationWrap;
import bo.union.platform.i16d.manager.filter.ArchiveConfigFtr;
import bo.union.platform.i16d.manager.wrapper.ArchiveWrap;

/**
 *
 * @author sipaco
 */
@Path("archive/config")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ArchiveConfigRest {

	@EJB
	private ArchiveConfigServ serv;
	@EJB
	private ArchiveTypeServ servType;

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
	 * Listado de Archivos's hijos
	 * 
	 * @param value
	 * @return
	 * @throws ServiceException
	 */
	@POST
	@Path("tree")
	public ArchiveWrap tree(ArchiveWrap value) throws ServiceException {
		List<ArchiveConfig> listConfig;
		ArchiveType archType;
		ArchiveWrap archWrap = new ArchiveWrap();
		try {
			if (value != null && value.getParent() != null) {
				listConfig = serv.tree(value.getParent(), value.getCurrent());
				archType = serv.findArchiveType(value.getParent());
				archWrap.setListConfig(listConfig);
				archWrap.setParent(archType);
			}
			return archWrap;
		} catch (ServiceException e) {
			throw e;
		}
	}

	/**
	 * Listado de Archivos
	 * 
	 * @param filter
	 * @return
	 * @throws ServiceException
	 */
	@POST
	@Path("filter")
	public List<ArchiveConfig> filterArchiveConfig(ArchiveConfigFtr filter) throws ServiceException {
		return serv.filterArchiveConfig(filter);
	}

	/**
	 * Creacion Archivo
	 * 
	 * @param value
	 * @return
	 * @throws ServiceException
	 */
	@POST
	@Path("create")
	public ArchiveConfig createArchiveConfig(ArchiveWrap value) throws ServiceException {
		try {
			ConfigurationWrap configWrap = serv.createArchiveConfig(value.getParent(), value.getCurrent());
			HTTPStatic.info("Se ha guardado el registro: " + configWrap.getArchiveConfig().getName());
			return configWrap.getArchiveConfig();
		} catch (ServiceException e) {
			HTTPStatic.error("No se pudo guardar el registro: " + value);
			throw e;
		}
	}

	/**
	 * Actualizacion Archivo
	 * 
	 * @param value
	 * @return
	 * @throws ServiceException
	 */
	@POST
	@Path("update")
	public ArchiveConfig updateArchiveConfig(ArchiveWrap value) throws ServiceException {
		try {
			ConfigurationWrap wrap = serv.updateArchiveConfig(value.getParent(), value.getCurrent());
			HTTPStatic.info("Se ha actualizado el registro: " + wrap.getArchiveConfig().getName());
			return wrap.getArchiveConfig();
		} catch (ServiceException e) {
			HTTPStatic.error("No se pudo actualizar el registro: " + value.getCurrent());
			throw e;
		}
	}

	/**
	 * Eliminacion Archivo
	 * 
	 * @param value
	 * @return
	 * @throws ServiceException
	 */
	@POST
	@Path("remove")
	public ArchiveConfig removeArchiveConfig(ArchiveConfig value) throws ServiceException {
		try {
			serv.removeArchiveConfig(value);
			HTTPStatic.info("Se ha eliminado el registro: " + value.getName());
			return value;
		} catch (ServiceException e) {
			HTTPStatic.error("No se pudo eliminar el registro: " + value);
			throw e;
		}
	}

	@POST
	@Path("validate")
	public ArchiveType validateArchiveType(ArchiveType value) throws ServiceException {
		servType.validateArchiveType(value);
		return value;
	}
}

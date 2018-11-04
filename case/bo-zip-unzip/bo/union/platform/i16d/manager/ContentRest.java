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

import bo.union.platform.i16d.manager.data.Content;
import bo.union.platform.i16d.manager.filter.ContentFtr;
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
import bo.union.platform.i16d.manager.data.Error;
import bo.union.platform.i16d.manager.local.HelpLocal;
import bo.union.platform.i16d.manager.wrapper.ArchiveWrap;
import java.util.ArrayList;

@Path("content")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ContentRest {

	@EJB
	private ContentServ serv;
	@EJB
	private ValueArchiveServ archiveServ;
	@EJB
	private ErrorServ errorServ;
	@EJB
	private ArchiveTypeServ typeServ;

	@GET
	@Path("info")
	public String info() {
		return "Info Service: " + this + " by " + serv;
	}

	@POST
	@Path("filter")
	public List<Content> filterContent(ContentFtr filter) throws ServiceException {
		return serv.filterContent(filter);
	}

	@POST
	@Path("create")
	public Content createContent(Content value) throws ServiceException {
		try {
			Content result = serv.createContent(value);
			HTTPStatic.info("Se ha guardado el registro: " + result.getContentId());
			return result;
		} catch (ServiceException e) {
			HTTPStatic.error("No se pudo guardar el registro: " + value);
			throw e;
		}
	}

	@POST
	@Path("update")
	public Content updateContent(Content value) throws ServiceException {
		try {
			Content result = serv.updateContent(value);
			HTTPStatic.info("Se ha actualizado el registro: " + result.getContentId());
			return result;
		} catch (ServiceException e) {
			HTTPStatic.error("No se pudo actualizar el registro: " + value);
			throw e;
		}
	}

	@POST
	@Path("remove")
	public Content removeContent(Content value) throws ServiceException {
		try {
			Content result = serv.removeContent(value);
			HTTPStatic.info("Se ha eliminado el registro: " + result.getContentId());
			return result;
		} catch (ServiceException e) {
			HTTPStatic.error("No se pudo eliminar el registro: " + value);
			throw e;
		}
	}

	@POST
	@Path("upload")
	public Content uploadContent(ArchiveWrap value) throws ServiceException {
		try {
			Content content = serv.proccessContent(value.getListContent(), value.getValueArchive());
			List<Error> result = serv.proccessData(value.getListContent(), content, "upload");
			value.getValueArchive().setContent(content);
			String textoBody = null;
			if (result.isEmpty()) {
				value.getValueArchive().setAttemptState(HelpLocal.STATE_LOAD);
				ValueArchive resArchive = archiveServ.createValueArchive(value.getValueArchive());
				textoBody = value.getValueArchive().getValue().getName() + " con éxito.";
				typeServ.sendNotification(resArchive.getArchiveType(), textoBody);
			} else {
				value.getValueArchive().setAttemptState(HelpLocal.STATE_ERROR);
				ValueArchive resultArchive = archiveServ.createValueArchive(value.getValueArchive());
				List<Error> listError = new ArrayList<>();
				for (Error er : result) {
					er.setValueArchive(resultArchive);
					er.setAttempt(resultArchive.getAttemptNro() + "");
					Error cError = errorServ.createError(er);
					listError.add(cError);
				}
				textoBody = value.getValueArchive().getValue().getName() + " con errores.";
				typeServ.sendNotification(resultArchive.getArchiveType(), textoBody);
			}
			HTTPStatic.info("Se ha cargado los archivos de : " + value);
			return content;
		} catch (ServiceException e) {
			HTTPStatic.error("No se pudo cargar los archivos : " + value);
			throw e;
		}
	}

	@POST
	@Path("reload")
	public Content reloadContent(ArchiveWrap value) throws ServiceException {
		try {
			if (!"LOAD".equals(value.getValueArchive().getAttemptState())) {
				Content content = serv.reproccesssContent(value.getListContent());
				List<Error> result = serv.proccessData(value.getListContent(), null, "reload");
				value.getValueArchive().setContent(content);
				String textoBody = null;
				if (result.isEmpty()) {
					value.getValueArchive().setAttemptState(HelpLocal.STATE_LOAD);
					textoBody = value.getValueArchive().getValue().getName() + " con éxito.";
					typeServ.sendNotification(value.getValueArchive().getArchiveType(), textoBody);
				} else {
					List<Error> listError = new ArrayList<>();
					for (Error er : result) {
						er.setValueArchive(value.getValueArchive());
						Integer attempNro = value.getValueArchive().getAttemptNro() + 1;
						er.setAttempt(attempNro.toString());
						Error cError = errorServ.createError(er);
						listError.add(cError);
					}
					value.getValueArchive().setAttemptState(HelpLocal.STATE_ERROR);
					value.getValueArchive().setErrorList(listError);
					textoBody = value.getValueArchive().getValue().getName() + " con errores.";
					typeServ.sendNotification(value.getValueArchive().getArchiveType(), textoBody);
				}
				archiveServ.updateValueArchive(value.getValueArchive());
				HTTPStatic.info("Se ha re-cargado los archivos de : " + value);
				return null;
			} else {
				HTTPStatic.info("Ya fue cargado los archivos exitosamente!!!");
				return null;
			}
		} catch (ServiceException e) {
			HTTPStatic.error("No se pudo re-cargar los archivos : " + value);
			throw e;
		}
	}

	@POST
	@Path("tree")
	public List<Content> treeUpload(ValueArchive value) throws ServiceException {
		try {
			return serv.treeUpload(value);
		} catch (ServiceException e) {
			HTTPStatic.error("No se pudo listar la configuracion de archivos: " + value);
			throw e;
		}
	}
}

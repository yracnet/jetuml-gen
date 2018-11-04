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

import bo.union.param.data.ParamItem;
import bo.union.param.data.ParamValue;
import bo.union.param.ParamProvider;
import bo.union.web.HTTPStatic;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Publicacion REST para la interface: ValueServ
 */
@Path("value")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ValueRest {

	@EJB(name = ParamProvider.NAME)
	private ParamProvider provider;
	protected final Map<String, String> paramMap = new HashMap<>();

	@POST
	@Path("param")
	public Map<String, List<ParamValue>> tokenParam() {
		Map<String, List<ParamValue>> option = new HashMap<>();
		option.put("state", getParam("VALUE_STATE", null));
		option.put("tokenType", getParam("TOKEN_TYPE", null));
		option.put("attemptState", getParam("ATTEMPT_STATE", null));
		option.put("archiveType", getParam("ARCHIVE_TYPE", null));
		option.put("certificateType", getParam("CERTIFICATE_TYPE", null));
		option.put("algSignature", getParam("ALG_SIGNATURE", null));
		option.put("algDigest", getParam("ALG_DIGEST", null));
		option.put("algEncrypt", getParam("ALG_ENCRYPT", null));
		option.put("filetype", getParam("ARCHIVE_FORMAT", null));
		////
		option.put("behavior", getParam("BEHAVIOR", null));
		option.put("csvcomillas", getParam("CSV_COMMA", null));
		option.put("csvseparador", getParam("CSV_SPLIT", null));
		option.put("formatcolumna", getParam("FORMAT_COLUMN", null));
		option.put("typecolumna", getParam("TYPE_COLUMN", null));
		return option;
	}

	private List<ParamValue> getParam(String name, String parent) {
		List<ParamValue> listParam = new ArrayList<>();
		try {
			ParamItem pi = new ParamItem();
			pi.setModule("I16D-MANAGER");
			pi.setDomain("I16D_EN");
			pi.setName(name);
			pi.setParent(parent);
			listParam = provider.paramList(pi);
		} catch (Exception e) {
			HTTPStatic.error("No se pudo listar formats de columna " + e);
		}
		return listParam;
	}
}

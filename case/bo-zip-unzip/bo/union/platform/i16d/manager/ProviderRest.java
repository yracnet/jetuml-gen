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
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 *
 * @author wyujra
 */
public class ProviderRest {

	@EJB(name = ParamProvider.NAME)
	private ParamProvider provider;
	protected final Map<String, String> paramMap = new HashMap<>();

	@POST
	@Path("param")
	public Map<String, List<ParamValue>> param() {
		Map<String, List<ParamValue>> option = new HashMap<>();
		paramMap.forEach((name, code) -> option.put(name, getParam(code, null)));
		return option;
	}

	@POST
	@Path("param/{name : (/name)?}{parent : (:parent)?}")
	public List<ParamValue> param(@PathParam("name") String name, @PathParam("parent") String parent) {
		String code = paramMap.get(name);
		return getParam(code, parent);
	}

	private List<ParamValue> getParam(String name, String parent) {
		List<ParamValue> listParam = new ArrayList<>();
		try {
			ParamItem pi = new ParamItem();
			pi.setDomain("I16D-MANAGER");
			pi.setName(name);
			if (parent != null) {
				pi.setName(name + "_" + parent);
				pi.setParent(parent);
			}
			listParam = provider.paramList(pi);
		} catch (Exception e) {
			HTTPStatic.error("No se pudo listar formats de columna " + e);
		}
		return listParam;
	}
}

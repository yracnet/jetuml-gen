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
package bo.union.platform.i16d.manager.mapper;

import java.util.List;
import java.util.ArrayList;
import bo.union.persist.police.PoliceMapper;
import bo.union.platform.i16d.manager.entity.I16dDeployment;
import bo.union.platform.i16d.manager.data.Deployment;
import bo.union.platform.i16d.manager.entity.I16dDeploymentExt;

public final class DeploymentMapper extends PoliceMapper {

	private static void mapperToI16dDeployment(Deployment from, I16dDeployment to) {
		to.setDeploymentId(from.getDeploymentId());
		to.setName(from.getName());
		to.setDescription(from.getDescription());
		to.setType(from.getType());
		to.setSubtype(from.getSubtype());
		I16dDeploymentExt toExt = new I16dDeploymentExt();
		toExt.setDeploymentId(from.getDeploymentId());
		toExt.setActive(from.getActive());
		toExt.setLogger(from.getLogger());
		toExt.setVersionId(from.getVersionId());
		mapperToAuditPartial(from, toExt);
		to.setI16dDeploymentExt(toExt);
	}

	private static void mapperToDeployment(I16dDeployment from, Deployment to) {
		to.setDeploymentId(from.getDeploymentId());
		to.setName(from.getName());
		to.setDescription(from.getDescription());
		to.setType(from.getType());
		to.setSubtype(from.getSubtype());
		I16dDeploymentExt ext = from.getI16dDeploymentExt();
		if (ext != null) {
			to.setActive(ext.isActive());
			to.setLogger(ext.isLogger());
			to.setVersionId(ext.getVersionId());
			mapperToPoliceBase(ext, to);
		}
	}

	public static Deployment mapperToDeployment(I16dDeployment from) {
		if (from == null) {
			return null;
		}
		Deployment to = new Deployment();
		mapperToDeployment(from, to);
		return to;
	}

	public static List<I16dDeployment> mapperToI16dDeploymentList(List<Deployment> fromList) {
		if (fromList == null) {
			return null;
		}
		List<I16dDeployment> toList = new ArrayList();
		fromList.stream().forEach(from -> {
			I16dDeployment to = mapperToI16dDeployment(from);
			toList.add(to);
		});
		return toList;
	}

	public static I16dDeployment mapperToI16dDeployment(Deployment from) {
		if (from == null) {
			return null;
		}
		I16dDeployment to = new I16dDeployment();
		mapperToI16dDeployment(from, to);
		return to;
	}

	public static List<Deployment> mapperToDeploymentList(List<I16dDeployment> fromList) {
		if (fromList == null) {
			return null;
		}
		List<Deployment> toList = new ArrayList();
		fromList.stream().forEach(from -> {
			Deployment to = mapperToDeployment(from);
			toList.add(to);
		});
		return toList;
	}
}

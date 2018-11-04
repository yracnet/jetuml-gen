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
import bo.union.platform.i16d.manager.data.Deployment;
import bo.union.platform.i16d.manager.entity.I16dPrivilege;
import bo.union.platform.i16d.manager.data.Privilege;

public final class PrivilegeMapper extends PoliceMapper {

	private static void mapperToI16dPrivilege(Privilege from, I16dPrivilege to) {
		to.setPrivilegeId(from.getPrivilegeId());
		mapperToAuditPartial(from, to);
	}

	private static void mapperToPrivilege(I16dPrivilege from, Privilege to) {
		to.setPrivilegeId(from.getPrivilegeId());
		to.setBeanId(from.getI16dBean().getBeanId());
		to.setVersionId(from.getVersionId());
		Deployment value = DeploymentMapper.mapperToDeployment(from.getI16dDeployment());
		to.setDeployment(value);
		mapperToPoliceBase(from, to);
	}

	public static Privilege mapperToPrivilege(I16dPrivilege from) {
		if (from == null) {
			return null;
		}
		Privilege to = new Privilege();
		mapperToPrivilege(from, to);
		return to;
	}

	public static List<I16dPrivilege> mapperToI16dPrivilegeList(List<Privilege> fromList) {
		if (fromList == null) {
			return null;
		}
		List<I16dPrivilege> toList = new ArrayList();
		fromList.stream().forEach(from -> {
			I16dPrivilege to = mapperToI16dPrivilege(from);
			toList.add(to);
		});
		return toList;
	}

	public static I16dPrivilege mapperToI16dPrivilege(Privilege from) {
		if (from == null) {
			return null;
		}
		I16dPrivilege to = new I16dPrivilege();
		mapperToI16dPrivilege(from, to);
		return to;
	}

	public static List<Privilege> mapperToPrivilegeList(List<I16dPrivilege> fromList) {
		if (fromList == null) {
			return null;
		}
		List<Privilege> toList = new ArrayList();
		fromList.stream().forEach(from -> {
			Privilege to = mapperToPrivilege(from);
			toList.add(to);
		});
		return toList;
	}
}
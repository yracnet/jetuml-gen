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

import bo.union.persist.police.PoliceMapper;
import java.util.List;
import java.util.ArrayList;
import bo.union.platform.i16d.manager.entity.I16dVersion;
import bo.union.platform.i16d.manager.data.Version;

public final class VersionMapper extends PoliceMapper {

	private static void mapperToI16dVersion(Version from, I16dVersion to) {
		to.setVersionId(from.getVersionId());
		to.setDeploymentId(from.getDeploymentId());
		to.setVersion(from.getValueVersion());
		to.setName(from.getName());
		to.setDescription(from.getDescription());
		to.setType(from.getType());
	}

	private static void mapperToVersion(I16dVersion from, Version to) {
		to.setVersionId(from.getVersionId());
		to.setDeploymentId(from.getDeploymentId());
		to.setValueVersion(from.getVersion());
		to.setName(from.getName());
		to.setDescription(from.getDescription());
		to.setType(from.getType());
	}

	public static Version mapperToVersion(I16dVersion from) {
		if (from == null) {
			return null;
		}
		Version to = new Version();
		mapperToVersion(from, to);
		return to;
	}

	public static List<I16dVersion> mapperToI16dVersionList(List<Version> fromList) {
		if (fromList == null) {
			return null;
		}
		List<I16dVersion> toList = new ArrayList();
		fromList.stream().forEach(from -> {
			I16dVersion to = mapperToI16dVersion(from);
			toList.add(to);
		});
		return toList;
	}

	public static I16dVersion mapperToI16dVersion(Version from) {
		if (from == null) {
			return null;
		}
		I16dVersion to = new I16dVersion();
		mapperToI16dVersion(from, to);
		return to;
	}

	public static List<Version> mapperToVersionList(List<I16dVersion> fromList) {
		if (fromList == null) {
			return null;
		}
		List<Version> toList = new ArrayList();
		fromList.stream().forEach(from -> {
			Version to = mapperToVersion(from);
			toList.add(to);
		});
		return toList;
	}
}

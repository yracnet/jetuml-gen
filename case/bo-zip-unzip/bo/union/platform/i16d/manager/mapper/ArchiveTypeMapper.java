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
import bo.union.platform.i16d.manager.entity.I16dArchiveType;
import bo.union.platform.i16d.manager.data.ArchiveType;
import java.util.Collections;

public final class ArchiveTypeMapper extends PoliceMapper {

	private static void mapperToI16dArchiveType(ArchiveType from, I16dArchiveType to) {
		to.setArchiveTypeId(from.getArchiveTypeId());
		to.setCode(from.getCode());
		to.setOrigin(from.getOrigin());
		to.setSender(from.getSender());
		to.setFormat(from.getFormat());
		to.setBehavior(from.getBehavior());
		to.setDescription(from.getDescription());
		to.setEmailList(from.getEmailList());
		mapperToAuditPartial(from, to);
	}

	private static void mapperToArchiveType(I16dArchiveType from, ArchiveType to) {
		to.setArchiveTypeId(from.getArchiveTypeId());
		to.setCode(from.getCode());
		to.setOrigin(from.getOrigin());
		to.setSender(from.getSender());
		to.setFormat(from.getFormat());
		to.setBehavior(from.getBehavior());
		to.setDescription(from.getDescription());
		to.setEmailList(from.getEmailList());
		mapperToPoliceBase(from, to);
	}

	public static ArchiveType mapperToArchiveType(I16dArchiveType from) {
		if (from == null) {
			return null;
		}
		ArchiveType to = new ArchiveType();
		mapperToArchiveType(from, to);
		return to;
	}

	public static List<I16dArchiveType> mapperToI16dArchiveTypeList(List<ArchiveType> fromList) {
		if (fromList == null) {
			return Collections.emptyList();
		}
		List<I16dArchiveType> toList = new ArrayList();
		fromList.stream().forEach(from -> {
			I16dArchiveType to = mapperToI16dArchiveType(from);
			toList.add(to);
		});
		return toList;
	}

	public static I16dArchiveType mapperToI16dArchiveType(ArchiveType from) {
		if (from == null) {
			return null;
		}
		I16dArchiveType to = new I16dArchiveType();
		mapperToI16dArchiveType(from, to);
		return to;
	}

	public static List<ArchiveType> mapperToArchiveTypeList(List<I16dArchiveType> fromList) {
		if (fromList == null) {
			return Collections.emptyList();
		}
		List<ArchiveType> toList = new ArrayList();
		fromList.stream().forEach(from -> {
			ArchiveType to = mapperToArchiveType(from);
			toList.add(to);
		});
		return toList;
	}
}

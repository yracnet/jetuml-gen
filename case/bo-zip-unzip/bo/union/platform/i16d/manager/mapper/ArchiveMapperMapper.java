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
import bo.union.platform.i16d.manager.entity.I16dArchiveMapper;
import bo.union.platform.i16d.manager.data.ArchiveMapper;
import java.util.Collections;

public final class ArchiveMapperMapper extends PoliceMapper {

	private static void mapperToI16dArchiveMapper(ArchiveMapper from, I16dArchiveMapper to) {
		to.setArchiveMapperId(from.getArchiveMapperId());
		to.setColTable(from.getColTable());
		to.setColArchive(from.getColArchive());
		to.setType(from.getType());
		to.setName(from.getName());
		to.setFormat(from.getFormat());
		to.setIsRequired(from.getIsRequired());
		to.setIsInsertable(from.getIsInsertable());
		to.setValueInicial(from.getValueInicial());
		to.setOrderBy(from.getOrderBy());
		to.setSizeValue(from.getSizeValue());
		mapperToAuditPartial(from, to);
	}

	private static void mapperToArchiveMapper(I16dArchiveMapper from, ArchiveMapper to) {
		to.setArchiveMapperId(from.getArchiveMapperId());
		to.setColTable(from.getColTable());
		to.setColArchive(from.getColArchive());
		to.setType(from.getType());
		to.setName(from.getName());
		to.setFormat(from.getFormat());
		to.setIsRequired(from.getIsRequired());
		to.setIsInsertable(from.getIsInsertable());
		to.setValueInicial(from.getValueInicial());
		to.setOrderBy(from.getOrderBy());
		to.setSizeValue(from.getSizeValue());
		mapperToPoliceBase(from, to);
	}

	public static ArchiveMapper mapperToArchiveMapper(I16dArchiveMapper from) {
		if (from == null) {
			return null;
		}
		ArchiveMapper to = new ArchiveMapper();
		mapperToArchiveMapper(from, to);
		return to;
	}

	public static List<I16dArchiveMapper> mapperToI16dArchiveMapperList(List<ArchiveMapper> fromList) {
		if (fromList == null) {
			return Collections.emptyList();
		}
		List<I16dArchiveMapper> toList = new ArrayList();
		fromList.stream().forEach(from -> {
			I16dArchiveMapper to = mapperToI16dArchiveMapper(from);
			toList.add(to);
		});
		return toList;
	}

	public static I16dArchiveMapper mapperToI16dArchiveMapper(ArchiveMapper from) {
		if (from == null) {
			return null;
		}
		I16dArchiveMapper to = new I16dArchiveMapper();
		mapperToI16dArchiveMapper(from, to);
		return to;
	}

	public static List<ArchiveMapper> mapperToArchiveMapperList(List<I16dArchiveMapper> fromList) {
		if (fromList == null) {
			return Collections.emptyList();
		}
		List<ArchiveMapper> toList = new ArrayList();
		fromList.stream().forEach(from -> {
			ArchiveMapper to = mapperToArchiveMapper(from);
			toList.add(to);
		});
		return toList;
	}
}

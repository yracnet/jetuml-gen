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
import bo.union.platform.i16d.manager.entity.I16dArchiveConfig;
import bo.union.platform.i16d.manager.data.ArchiveConfig;
import bo.union.platform.i16d.manager.data.ArchiveMapper;
import bo.union.platform.i16d.manager.entity.I16dArchiveMapper;
import static bo.union.platform.i16d.manager.mapper.ArchiveMapperMapper.mapperToArchiveMapperList;
import static bo.union.platform.i16d.manager.mapper.ArchiveMapperMapper.mapperToI16dArchiveMapperList;
import java.util.Collections;

public final class ArchiveConfigMapper extends PoliceMapper {

	private static void mapperToI16dArchiveConfig(ArchiveConfig from, I16dArchiveConfig to) {
		to.setArchiveConfigId(from.getArchiveConfigId());
		to.setName(from.getName());
		to.setDescription(from.getDescription());
		to.setValProcedure(from.getValProcedure());
		to.setProcessable(from.getProcessable());
		to.setMaxSize(from.getMaxSize());
		to.setNameTabla(from.getNameTabla());
		to.setCsvSplit(from.getCsvSplit());
		to.setCsvHead(from.getCsvHead());
		to.setCsvComma(from.getCsvComma());
		to.setXlsSheet(from.getXlsSheet());
		to.setXlsHead(from.getXlsHead());
		if (from.getMapeoList() != null) {
			List<I16dArchiveMapper> mapeoList = mapperToI16dArchiveMapperList(from.getMapeoList());
			to.setI16dArchiveMapperList(mapeoList);
		}
		mapperToAuditPartial(from, to);
	}

	private static void mapperToArchiveConfig(I16dArchiveConfig from, ArchiveConfig to) {
		to.setArchiveConfigId(from.getArchiveConfigId());
		to.setName(from.getName());
		to.setDescription(from.getDescription());
		to.setValProcedure(from.getValProcedure());
		to.setProcessable(from.getProcessable());
		to.setMaxSize(from.getMaxSize());
		to.setNameTabla(from.getNameTabla());
		to.setCsvSplit(from.getCsvSplit());
		to.setCsvHead(from.getCsvHead());
		to.setCsvComma(from.getCsvComma());
		to.setXlsSheet(from.getXlsSheet());
		to.setXlsHead(from.getXlsHead());
		if (from.getI16dArchiveMapperList() != null) {
			List<ArchiveMapper> mapeoList = mapperToArchiveMapperList(from.getI16dArchiveMapperList());
			to.setMapeoList(mapeoList);
		}
		mapperToPoliceBase(from, to);
	}

	public static ArchiveConfig mapperToArchiveConfig(I16dArchiveConfig from) {
		if (from == null) {
			return null;
		}
		ArchiveConfig to = new ArchiveConfig();
		mapperToArchiveConfig(from, to);
		return to;
	}

	public static List<I16dArchiveConfig> mapperToI16dArchiveConfigList(List<ArchiveConfig> fromList) {
		if (fromList == null) {
			return Collections.emptyList();
		}
		List<I16dArchiveConfig> toList = new ArrayList();
		fromList.stream().forEach(from -> {
			I16dArchiveConfig to = mapperToI16dArchiveConfig(from);
			toList.add(to);
		});
		return toList;
	}

	public static I16dArchiveConfig mapperToI16dArchiveConfig(ArchiveConfig from) {
		if (from == null) {
			return null;
		}
		I16dArchiveConfig to = new I16dArchiveConfig();
		mapperToI16dArchiveConfig(from, to);
		return to;
	}

	public static List<ArchiveConfig> mapperToArchiveConfigList(List<I16dArchiveConfig> fromList) {
		if (fromList == null) {
			return Collections.emptyList();
		}
		List<ArchiveConfig> toList = new ArrayList();
		fromList.stream().forEach(from -> {
			ArchiveConfig to = mapperToArchiveConfig(from);
			toList.add(to);
		});
		return toList;
	}
}

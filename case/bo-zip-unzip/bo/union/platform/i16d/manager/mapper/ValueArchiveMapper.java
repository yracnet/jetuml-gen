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
import bo.union.platform.i16d.manager.data.ArchiveType;
import bo.union.platform.i16d.manager.data.Content;
import bo.union.platform.i16d.manager.data.Value;
import bo.union.platform.i16d.manager.entity.I16dValueArchive;
import bo.union.platform.i16d.manager.data.ValueArchive;
import bo.union.platform.i16d.manager.entity.I16dArchiveType;
import bo.union.platform.i16d.manager.entity.I16dContent;
import bo.union.platform.i16d.manager.entity.I16dValue;
import java.util.Collections;

public final class ValueArchiveMapper extends PoliceMapper {

	private static void mapperToI16dValueArchive(ValueArchive from, I16dValueArchive to) {
		to.setValueArchiveId(from.getValueArchiveId());
		I16dArchiveType valueType = ArchiveTypeMapper.mapperToI16dArchiveType(from.getArchiveType());
		to.setI16dArchiveType(valueType);
		to.setAttemptNro(from.getAttemptNro());
		to.setAttemptState(from.getAttemptState());
		I16dContent content = ContentMapper.mapperToI16dContent(from.getContent());
		to.setI16dContent(content);
		to.setDateFrom(from.getDateFrom());
		to.setDateTo(ValueMapper.defaultDate(from.getDateTo()));
		I16dValue value = ValueMapper.mapperToI16dValue(from.getValue());
		to.setI16dValue(value);
		mapperToAuditPartial(from, to);
	}

	private static void mapperToValueArchive(I16dValueArchive from, ValueArchive to) {
		to.setValueArchiveId(from.getValueArchiveId());
		to.setAttemptNro(from.getAttemptNro());
		to.setAttemptState(from.getAttemptState());
		if (from.getI16dContent() != null) {
			Content content = ContentMapper.mapperToContent(from.getI16dContent());
			to.setContent(content);
		}
		to.setDateFrom(from.getDateFrom());
		to.setDateTo(ValueMapper.defaultDate(from.getDateTo()));
		I16dArchiveType typeE = from.getI16dArchiveType();
		if (typeE != null) {
			ArchiveType type = ArchiveTypeMapper.mapperToArchiveType(typeE);
			to.setArchiveType(type);
		}
		Value value = ValueMapper.mapperToValue(from.getI16dValue());
		to.setValue(value);
		mapperToPoliceBase(from, to);
	}

	public static ValueArchive mapperToValueArchive(I16dValueArchive from) {
		if (from == null) {
			return null;
		}
		ValueArchive to = new ValueArchive();
		mapperToValueArchive(from, to);
		return to;
	}

	public static List<I16dValueArchive> mapperToI16dValueArchiveList(List<ValueArchive> fromList) {
		if (fromList == null) {
			return Collections.emptyList();
		}
		List<I16dValueArchive> toList = new ArrayList();
		fromList.stream().forEach(from -> {
			I16dValueArchive to = mapperToI16dValueArchive(from);
			toList.add(to);
		});
		return toList;
	}

	public static I16dValueArchive mapperToI16dValueArchive(ValueArchive from) {
		if (from == null) {
			return null;
		}
		I16dValueArchive to = new I16dValueArchive();
		mapperToI16dValueArchive(from, to);
		return to;
	}

	public static List<ValueArchive> mapperToValueArchiveList(List<I16dValueArchive> fromList) {
		if (fromList == null) {
			return Collections.emptyList();
		}
		List<ValueArchive> toList = new ArrayList();
		fromList.stream().forEach(from -> {
			ValueArchive to = mapperToValueArchive(from);
			toList.add(to);
		});
		return toList;
	}
}

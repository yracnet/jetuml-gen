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
import bo.union.platform.i16d.manager.data.Value;
import bo.union.platform.i16d.manager.entity.I16dValueUrl;
import bo.union.platform.i16d.manager.data.ValueUrl;
import bo.union.platform.i16d.manager.entity.I16dValue;
import java.util.Collections;

public final class ValueUrlMapper extends PoliceMapper {

	private static void mapperToI16dValueUrl(ValueUrl from, I16dValueUrl to) {
		to.setValueUrlId(from.getValueUrlId());
		to.setUrlLocal(from.getUrlLocal());
		to.setUrlRemote(from.getUrlRemote());
		to.setConnectTimeout(from.getConnectTimeout());
		to.setReadTimeout(from.getReadTimeout());
		to.setIsRegex(from.getIsRegex());
		to.setDateFrom(from.getDateFrom());
		to.setDateTo(from.getDateTo());
		I16dValue value = ValueMapper.mapperToI16dValue(from.getValue());
		to.setI16dValue(value);
		mapperToAuditPartial(from, to);
	}

	private static void mapperToValueUrl(I16dValueUrl from, ValueUrl to) {
		to.setValueUrlId(from.getValueUrlId());
		to.setUrlLocal(from.getUrlLocal());
		to.setUrlRemote(from.getUrlRemote());
		to.setConnectTimeout(from.getConnectTimeout());
		to.setReadTimeout(from.getReadTimeout());
		to.setIsRegex(from.getIsRegex());
		to.setDateFrom(from.getDateFrom());
		to.setDateTo(ValueMapper.defaultDate(from.getDateTo()));
		Value value = ValueMapper.mapperToValue(from.getI16dValue());
		to.setValue(value);
		mapperToPoliceBase(from, to);
	}

	public static ValueUrl mapperToValueUrl(I16dValueUrl from) {
		if (from == null) {
			return null;
		}
		ValueUrl to = new ValueUrl();
		mapperToValueUrl(from, to);
		return to;
	}

	public static List<I16dValueUrl> mapperToI16dValueUrlList(List<ValueUrl> fromList) {
		if (fromList == null) {
			return null;
		}
		List<I16dValueUrl> toList = new ArrayList();
		fromList.stream().forEach(from -> {
			I16dValueUrl to = mapperToI16dValueUrl(from);
			toList.add(to);
		});
		return toList;
	}

	public static I16dValueUrl mapperToI16dValueUrl(ValueUrl from) {
		if (from == null) {
			return null;
		}
		I16dValueUrl to = new I16dValueUrl();
		mapperToI16dValueUrl(from, to);
		return to;
	}

	public static List<ValueUrl> mapperToValueUrlList(List<I16dValueUrl> fromList) {
		if (fromList == null) {
			return Collections.emptyList();
		}
		List<ValueUrl> toList = new ArrayList();
		fromList.stream().forEach(from -> {
			ValueUrl to = mapperToValueUrl(from);
			toList.add(to);
		});
		return toList;
	}
}
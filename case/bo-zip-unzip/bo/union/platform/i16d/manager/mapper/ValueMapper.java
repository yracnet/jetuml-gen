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
import bo.union.platform.i16d.manager.entity.I16dValue;
import bo.union.platform.i16d.manager.data.Value;
import java.util.Collections;
import java.util.Date;
import java.util.function.Function;

public final class ValueMapper extends PoliceMapper {

	public static void mapperToI16dValue(Value from, I16dValue to) {
		to.setValueId(from.getValueId());
		to.setName(from.getName());
		to.setState(from.getState());
		to.setType(from.getType());
		to.setDescription(from.getDescription());
		mapperToAuditPartial(from, to);
	}

	private static void mapperToValue(I16dValue from, Value to) {
		to.setValueId(from.getValueId());
		to.setName(from.getName());
		to.setState(from.getState());
		to.setType(from.getType());
		to.setDescription(from.getDescription());
		mapperToPoliceBase(from, to);
	}

	public static Value mapperToValue(I16dValue from) {
		if (from == null) {
			return null;
		}
		Value to = new Value();
		mapperToValue(from, to);
		return to;
	}

	public static List<I16dValue> mapperToI16dValueList(List<Value> fromList) {
		if (fromList == null) {
			return Collections.emptyList();
		}
		List<I16dValue> toList = new ArrayList();
		fromList.stream().forEach(from -> {
			I16dValue to = mapperToI16dValue(from);
			toList.add(to);
		});
		return toList;
	}

	public static I16dValue mapperToI16dValue(Value from) {
		if (from == null) {
			return null;
		}
		I16dValue to = new I16dValue();
		mapperToI16dValue(from, to);
		return to;
	}

	public static List<Value> mapperToValueList(List<I16dValue> fromList) {
		return mapperToValueList(fromList, t -> t);
	}

	public static List<Value> mapperToValueList(List<I16dValue> fromList, Function<Value, Value> fn) {
		if (fromList == null) {
			return Collections.emptyList();
		}
		List<Value> toList = new ArrayList();
		fromList.stream().forEach(from -> {
			Value to = mapperToValue(from);
			to = fn.apply(to);
			toList.add(to);
		});
		return toList;
	}
	private static final Long DEFAUTL_TIME = 253402228800000L;

	public static Date defaultDate(Date value) {
		return value == null ? new Date(DEFAUTL_TIME) : value;
	}
}

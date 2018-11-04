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
import bo.union.platform.i16d.manager.entity.I16dValueToken;
import bo.union.platform.i16d.manager.data.ValueToken;
import bo.union.platform.i16d.manager.entity.I16dValue;
import java.util.Collections;

public final class ValueTokenMapper extends PoliceMapper {

	public static void mapperToI16dValueToken(ValueToken from, I16dValueToken to) {
		to.setValueTokenId(from.getValueTokenId());
		to.setToken(from.getToken());
		to.setDateFrom(from.getDateFrom());
		to.setDateTo(ValueMapper.defaultDate(from.getDateTo()));
		to.setType(from.getType());
		I16dValue value = ValueMapper.mapperToI16dValue(from.getValue());
		to.setI16dValue(value);
		mapperToAuditPartial(from, to);
	}

	private static void mapperToValueToken(I16dValueToken from, ValueToken to) {
		to.setValueTokenId(from.getValueTokenId());
		to.setToken(from.getToken());
		to.setDateFrom(from.getDateFrom());
		to.setDateTo(from.getDateTo());
		to.setType(from.getType());
		Value value = ValueMapper.mapperToValue(from.getI16dValue());
		to.setValue(value);
		mapperToPoliceBase(from, to);
	}

	public static ValueToken mapperToValueToken(I16dValueToken from) {
		if (from == null) {
			return null;
		}
		ValueToken to = new ValueToken();
		mapperToValueToken(from, to);
		return to;
	}

	public static List<I16dValueToken> mapperToI16dValueTokenList(List<ValueToken> fromList) {
		if (fromList == null) {
			return Collections.emptyList();
		}
		List<I16dValueToken> toList = new ArrayList();
		fromList.stream().forEach(from -> {
			I16dValueToken to = mapperToI16dValueToken(from);
			toList.add(to);
		});
		return toList;
	}

	public static I16dValueToken mapperToI16dValueToken(ValueToken from) {
		if (from == null) {
			return null;
		}
		I16dValueToken to = new I16dValueToken();
		mapperToI16dValueToken(from, to);
		return to;
	}

	public static List<ValueToken> mapperToValueTokenList(List<I16dValueToken> fromList) {
		if (fromList == null) {
			return Collections.emptyList();
		}
		List<ValueToken> toList = new ArrayList();
		fromList.stream().forEach(from -> {
			ValueToken to = mapperToValueToken(from);
			toList.add(to);
		});
		return toList;
	}
}

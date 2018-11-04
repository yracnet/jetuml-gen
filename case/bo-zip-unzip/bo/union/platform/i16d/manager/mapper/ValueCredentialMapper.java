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
import bo.union.platform.i16d.manager.entity.I16dValueCredential;
import bo.union.platform.i16d.manager.data.ValueCredential;
import bo.union.platform.i16d.manager.entity.I16dValue;
import java.util.Collections;

public final class ValueCredentialMapper extends PoliceMapper {

	public static void mapperToI16dValueCredential(ValueCredential from, I16dValueCredential to) {
		to.setValueCredentialId(from.getValueCredentialId());
		to.setUsername(from.getUsername());
		// to.setPassword(from.getPassword());
		to.setDateFrom(from.getDateFrom());
		to.setDateTo(ValueMapper.defaultDate(from.getDateTo()));
		I16dValue value = ValueMapper.mapperToI16dValue(from.getValue());
		to.setI16dValue(value);
		mapperToAuditPartial(from, to);
	}

	private static void mapperToValueCredential(I16dValueCredential from, ValueCredential to) {
		to.setValueCredentialId(from.getValueCredentialId());
		to.setUsername(from.getUsername());
		// to.setPassword(from.getPassword());
		to.setDateFrom(from.getDateFrom());
		to.setDateTo(from.getDateTo());
		Value value = ValueMapper.mapperToValue(from.getI16dValue());
		to.setValue(value);
		mapperToPoliceBase(from, to);
	}

	public static ValueCredential mapperToValueCredential(I16dValueCredential from) {
		if (from == null) {
			return null;
		}
		ValueCredential to = new ValueCredential();
		mapperToValueCredential(from, to);
		return to;
	}

	public static List<I16dValueCredential> mapperToI16dValueCredentialList(List<ValueCredential> fromList) {
		if (fromList == null) {
			return Collections.emptyList();
		}
		List<I16dValueCredential> toList = new ArrayList();
		fromList.stream().forEach(from -> {
			I16dValueCredential to = mapperToI16dValueCredential(from);
			toList.add(to);
		});
		return toList;
	}

	public static I16dValueCredential mapperToI16dValueCredential(ValueCredential from) {
		if (from == null) {
			return null;
		}
		I16dValueCredential to = new I16dValueCredential();
		mapperToI16dValueCredential(from, to);
		return to;
	}

	public static List<ValueCredential> mapperToValueCredentialList(List<I16dValueCredential> fromList) {
		if (fromList == null) {
			return Collections.emptyList();
		}
		List<ValueCredential> toList = new ArrayList();
		fromList.stream().forEach(from -> {
			ValueCredential to = mapperToValueCredential(from);
			toList.add(to);
		});
		return toList;
	}
}

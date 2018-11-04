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
import bo.union.platform.i16d.manager.entity.I16dValueCertificate;
import bo.union.platform.i16d.manager.data.ValueCertificate;
import bo.union.platform.i16d.manager.entity.I16dValue;
import java.util.Collections;

public final class ValueCertificateMapper extends PoliceMapper {

	public static void mapperToI16dValueCertificate(ValueCertificate from, I16dValueCertificate to) {
		to.setValueCertificateId(from.getValueCertificateId());
		to.setType(from.getType());
		to.setNameSignature(from.getNameSignature());
		to.setAlgSignture(from.getAlgSignture());
		to.setAlgDigest(from.getAlgDigest());
		to.setAlgEncrypt(from.getAlgEncrypt());
		to.setDateFrom(from.getDateFrom());
		to.setDateTo(ValueMapper.defaultDate(from.getDateTo()));
		I16dValue value = ValueMapper.mapperToI16dValue(from.getValue());
		to.setI16dValue(value);
		mapperToAuditPartial(from, to);
	}

	private static void mapperToValueCertificate(I16dValueCertificate from, ValueCertificate to) {
		to.setValueCertificateId(from.getValueCertificateId());
		to.setType(from.getType());
		to.setNameSignature(from.getNameSignature());
		to.setSummary(from.getSummary());
		to.setAlgSignture(from.getAlgSignture());
		to.setAlgDigest(from.getAlgDigest());
		to.setAlgEncrypt(from.getAlgEncrypt());
		to.setDateFrom(from.getDateFrom());
		to.setDateTo(from.getDateTo());
		Value value = ValueMapper.mapperToValue(from.getI16dValue());
		to.setValue(value);
		mapperToPoliceBase(from, to);
	}

	public static ValueCertificate mapperToValueCertificate(I16dValueCertificate from) {
		if (from == null) {
			return null;
		}
		ValueCertificate to = new ValueCertificate();
		mapperToValueCertificate(from, to);
		return to;
	}

	public static List<I16dValueCertificate> mapperToI16dValueCertificateList(List<ValueCertificate> fromList) {
		if (fromList == null) {
			return Collections.emptyList();
		}
		List<I16dValueCertificate> toList = new ArrayList();
		fromList.stream().forEach(from -> {
			I16dValueCertificate to = mapperToI16dValueCertificate(from);
			toList.add(to);
		});
		return toList;
	}

	public static I16dValueCertificate mapperToI16dValueCertificate(ValueCertificate from) {
		if (from == null) {
			return null;
		}
		I16dValueCertificate to = new I16dValueCertificate();
		mapperToI16dValueCertificate(from, to);
		return to;
	}

	public static List<ValueCertificate> mapperToValueCertificateList(List<I16dValueCertificate> fromList) {
		if (fromList == null) {
			return Collections.emptyList();
		}
		List<ValueCertificate> toList = new ArrayList();
		fromList.stream().forEach(from -> {
			ValueCertificate to = mapperToValueCertificate(from);
			toList.add(to);
		});
		return toList;
	}
}

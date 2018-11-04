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
import bo.union.platform.i16d.manager.entity.I16dError;
import bo.union.platform.i16d.manager.data.Error;

public final class ErrorMapper extends PoliceMapper {

	private static void mapperToI16dError(Error from, I16dError to) {
		to.setErrorId(from.getErrorId());
		to.setAttempt(from.getAttempt());
		to.setDescription(from.getDescription());
		to.setNomArchive(from.getNomArchive());
		to.setNameColumn(from.getNameColumn());
		to.setAtLine(from.getAtLine());
		mapperToAuditPartial(from, to);
	}

	private static void mapperToError(I16dError from, Error to) {
		to.setErrorId(from.getErrorId());
		to.setAttempt(from.getAttempt());
		to.setDescription(from.getDescription());
		to.setNomArchive(from.getNomArchive());
		to.setNameColumn(from.getNameColumn());
		to.setAtLine(from.getAtLine());
		mapperToPoliceBase(from, to);
	}

	public static Error mapperToError(I16dError from) {
		if (from == null) {
			return null;
		}
		Error to = new Error();
		mapperToError(from, to);
		return to;
	}

	public static List<I16dError> mapperToI16dErrorList(List<Error> fromList) {
		if (fromList == null) {
			return null;
		}
		List<I16dError> toList = new ArrayList();
		fromList.stream().forEach(from -> {
			I16dError to = mapperToI16dError(from);
			toList.add(to);
		});
		return toList;
	}

	public static I16dError mapperToI16dError(Error from) {
		if (from == null) {
			return null;
		}
		I16dError to = new I16dError();
		mapperToI16dError(from, to);
		return to;
	}

	public static List<Error> mapperToErrorList(List<I16dError> fromList) {
		if (fromList == null) {
			return null;
		}
		List<Error> toList = new ArrayList();
		fromList.stream().forEach(from -> {
			Error to = mapperToError(from);
			toList.add(to);
		});
		return toList;
	}
}
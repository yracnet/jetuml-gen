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

import bo.union.persist.police.PoliceMapper;
import java.util.List;
import java.util.ArrayList;
import bo.union.platform.i16d.manager.data.Grouper;
import bo.union.platform.i16d.manager.entity.I16dGrouper;

public final class GrouperMapper extends PoliceMapper {

	public static void mapperToI16dGrouper(Grouper from, I16dGrouper to) {
		to.setGrouperId(from.getGrouperId());
		to.setName(from.getName());
		to.setDescription(from.getDescription());
		mapperToAuditPartial(from, to);
	}

	public static void mapperToGrouper(I16dGrouper from, Grouper to) {
		to.setGrouperId(from.getGrouperId());
		to.setName(from.getName());
		to.setDescription(from.getDescription());
		mapperToPoliceBase(from, to);
	}

	public static List<I16dGrouper> mapperToI16dGrouperList(List<Grouper> fromList) {
		List<I16dGrouper> toList = new ArrayList();
		fromList.forEach(from -> {
			I16dGrouper to = mapperToI16dGrouper(from);
			toList.add(to);
		});
		return toList;
	}

	public static Grouper mapperToGrouper(I16dGrouper from) {
		if (from == null) {
			return null;
		}
		Grouper to = new Grouper();
		mapperToGrouper(from, to);
		return to;
	}

	public static List<Grouper> mapperToGrouperList(List<I16dGrouper> fromList) {
		List<Grouper> toList = new ArrayList();
		fromList.forEach(from -> {
			Grouper to = mapperToGrouper(from);
			toList.add(to);
		});
		return toList;
	}

	public static I16dGrouper mapperToI16dGrouper(Grouper from) {
		if (from == null) {
			return null;
		}
		I16dGrouper to = new I16dGrouper();
		mapperToI16dGrouper(from, to);
		return to;
	}
}

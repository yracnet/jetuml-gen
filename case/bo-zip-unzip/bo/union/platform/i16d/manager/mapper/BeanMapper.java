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
import bo.union.platform.i16d.manager.entity.I16dBean;
import bo.union.platform.i16d.manager.data.Bean;

public final class BeanMapper extends PoliceMapper {

	private static void mapperToI16dBean(Bean from, I16dBean to) {
		to.setBeanId(from.getBeanId());
		to.setVersionId(from.getVersionId());
		to.setName(from.getName());
		to.setDescription(from.getDescription());
	}

	private static void mapperToBean(I16dBean from, Bean to) {
		to.setBeanId(from.getBeanId());
		to.setVersionId(from.getVersionId());
		to.setName(from.getName());
		to.setDescription(from.getDescription());
	}

	public static Bean mapperToBean(I16dBean from) {
		if (from == null) {
			return null;
		}
		Bean to = new Bean();
		mapperToBean(from, to);
		return to;
	}

	public static List<I16dBean> mapperToI16dBeanList(List<Bean> fromList) {
		if (fromList == null) {
			return null;
		}
		List<I16dBean> toList = new ArrayList();
		fromList.stream().forEach(from -> {
			I16dBean to = mapperToI16dBean(from);
			toList.add(to);
		});
		return toList;
	}

	public static I16dBean mapperToI16dBean(Bean from) {
		if (from == null) {
			return null;
		}
		I16dBean to = new I16dBean();
		mapperToI16dBean(from, to);
		return to;
	}

	public static List<Bean> mapperToBeanList(List<I16dBean> fromList) {
		if (fromList == null) {
			return null;
		}
		List<Bean> toList = new ArrayList();
		fromList.stream().forEach(from -> {
			Bean to = mapperToBean(from);
			toList.add(to);
		});
		return toList;
	}
}
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
import bo.union.platform.i16d.manager.entity.I16dBeanMethod;
import bo.union.platform.i16d.manager.data.BeanMethod;
import bo.union.platform.i16d.manager.entity.I16dBeanMethodExt;

public final class BeanMethodMapper extends PoliceMapper {

	private static void mapperToI16dBeanMethod(BeanMethod from, I16dBeanMethod to) {
		to.setBeanMethodId(from.getBeanMethodId());
		to.setBeanId(from.getBeanId());
		to.setName(from.getName());
		to.setDescription(from.getDescription());
		to.setMethodSignature(from.getMethodSignature());
		to.setBeanRef(from.getBeanRef());
		to.setBeanName(from.getBeanName());
		I16dBeanMethodExt toExt = new I16dBeanMethodExt();
		toExt.setBeanMethodId(from.getBeanMethodId());
		toExt.setActive(from.getActive());
		mapperToAuditPartial(from, toExt);
		to.setI16dBeanMethodExt(toExt);
	}

	private static void mapperToBeanMethod(I16dBeanMethod from, BeanMethod to) {
		to.setBeanMethodId(from.getBeanMethodId());
		to.setBeanId(from.getBeanId());
		to.setName(from.getName());
		to.setDescription(from.getDescription());
		to.setMethodSignature(from.getMethodSignature());
		to.setBeanRef(from.getBeanRef());
		to.setBeanName(from.getBeanName());
		I16dBeanMethodExt ext = from.getI16dBeanMethodExt();
		if (ext != null) {
			to.setActive(ext.isActive());
			mapperToPoliceBase(ext, to);
		}
	}

	public static BeanMethod mapperToBeanMethod(I16dBeanMethod from) {
		if (from == null) {
			return null;
		}
		BeanMethod to = new BeanMethod();
		mapperToBeanMethod(from, to);
		return to;
	}

	public static List<I16dBeanMethod> mapperToI16dBeanMethodList(List<BeanMethod> fromList) {
		if (fromList == null) {
			return null;
		}
		List<I16dBeanMethod> toList = new ArrayList();
		fromList.stream().forEach(from -> {
			I16dBeanMethod to = mapperToI16dBeanMethod(from);
			toList.add(to);
		});
		return toList;
	}

	public static I16dBeanMethod mapperToI16dBeanMethod(BeanMethod from) {
		if (from == null) {
			return null;
		}
		I16dBeanMethod to = new I16dBeanMethod();
		mapperToI16dBeanMethod(from, to);
		return to;
	}

	public static List<BeanMethod> mapperToBeanMethodList(List<I16dBeanMethod> fromList) {
		if (fromList == null) {
			return null;
		}
		List<BeanMethod> toList = new ArrayList();
		fromList.stream().forEach(from -> {
			BeanMethod to = mapperToBeanMethod(from);
			toList.add(to);
		});
		return toList;
	}
}

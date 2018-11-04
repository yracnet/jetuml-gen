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
import bo.union.platform.i16d.manager.entity.I16dContent;
import bo.union.platform.i16d.manager.data.Content;

public final class ContentMapper extends PoliceMapper {

	private static void mapperToI16dContent(Content from, I16dContent to) {
		to.setContentId(from.getContentId());
		to.setArchiveConfigId(from.getArchiveConfigId());
		to.setName(from.getName());
		to.setSummary(from.getSummary());
		mapperToAuditPartial(from, to);
	}

	private static void mapperToContent(I16dContent from, Content to) {
		to.setContentId(from.getContentId());
		to.setArchiveConfigId(from.getArchiveConfigId());
		to.setName(from.getName());
		to.setSummary(from.getSummary());
		mapperToPoliceBase(from, to);
	}

	public static Content mapperToContent(I16dContent from) {
		if (from == null) {
			return null;
		}
		Content to = new Content();
		mapperToContent(from, to);
		return to;
	}

	public static List<I16dContent> mapperToI16dContentList(List<Content> fromList) {
		if (fromList == null) {
			return null;
		}
		List<I16dContent> toList = new ArrayList();
		fromList.stream().forEach(from -> {
			I16dContent to = mapperToI16dContent(from);
			toList.add(to);
		});
		return toList;
	}

	public static I16dContent mapperToI16dContent(Content from) {
		if (from == null) {
			return null;
		}
		I16dContent to = new I16dContent();
		mapperToI16dContent(from, to);
		return to;
	}

	public static List<Content> mapperToContentList(List<I16dContent> fromList) {
		if (fromList == null) {
			return null;
		}
		List<Content> toList = new ArrayList();
		fromList.stream().forEach(from -> {
			Content to = mapperToContent(from);
			toList.add(to);
		});
		return toList;
	}
}
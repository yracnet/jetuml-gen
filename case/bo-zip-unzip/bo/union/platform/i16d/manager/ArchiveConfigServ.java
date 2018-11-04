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
package bo.union.platform.i16d.manager;

import java.util.List;
import bo.union.lang.ServiceException;
import bo.union.platform.i16d.manager.data.ArchiveConfig;
import bo.union.platform.i16d.manager.data.ArchiveType;
import bo.union.platform.i16d.manager.data.ConfigurationWrap;
import bo.union.platform.i16d.manager.filter.ArchiveConfigFtr;

public interface ArchiveConfigServ {

	public ArchiveType findArchiveType(ArchiveType archType) throws ServiceException;

	public List<ArchiveConfig> tree(ArchiveType archType, ArchiveConfig archConfig) throws ServiceException;

	public List<ArchiveConfig> filterArchiveConfig(ArchiveConfigFtr filter) throws ServiceException;

	public ConfigurationWrap createArchiveConfig(ArchiveType archiveType, ArchiveConfig value) throws ServiceException;

	public ConfigurationWrap updateArchiveConfig(ArchiveType archiveType, ArchiveConfig value) throws ServiceException;

	public ArchiveConfig removeArchiveConfig(ArchiveConfig value) throws ServiceException;
}

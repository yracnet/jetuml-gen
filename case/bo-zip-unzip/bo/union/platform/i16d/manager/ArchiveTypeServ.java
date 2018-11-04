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
import bo.union.platform.i16d.manager.data.ArchiveType;
import bo.union.platform.i16d.manager.filter.ArchiveTypeFtr;

public interface ArchiveTypeServ {

	public List<ArchiveType> filterArchiveType(ArchiveTypeFtr filter) throws ServiceException;

	public ArchiveType createArchiveType(ArchiveType value) throws ServiceException;

	public ArchiveType updateArchiveType(ArchiveType value) throws ServiceException;

	public ArchiveType removeArchiveType(ArchiveType value) throws ServiceException;

	public ArchiveType sendNotification(ArchiveType value, String textBody) throws ServiceException;

	public ArchiveType validateArchiveType(ArchiveType value) throws ServiceException;
}

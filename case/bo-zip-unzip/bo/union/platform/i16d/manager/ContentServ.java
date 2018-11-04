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
import bo.union.platform.i16d.manager.data.Content;
import bo.union.platform.i16d.manager.data.Error;
import bo.union.platform.i16d.manager.data.ValueArchive;
import bo.union.platform.i16d.manager.filter.ContentFtr;

public interface ContentServ {

	public List<Content> filterContent(ContentFtr filter) throws ServiceException;

	public List<Content> getContent(Long idContent) throws ServiceException;

	public Content createContent(Content value) throws ServiceException;

	public Content updateContent(Content value) throws ServiceException;

	public Content removeContent(Content value) throws ServiceException;

	public Content proccessContent(List<Content> value, ValueArchive archive) throws ServiceException;

	public Content reproccesssContent(List<Content> value) throws ServiceException;

	public List<Error> proccessData(List<Content> value, Content content, String proccessType) throws ServiceException;

	public List<Content> treeUpload(ValueArchive value) throws ServiceException;
}
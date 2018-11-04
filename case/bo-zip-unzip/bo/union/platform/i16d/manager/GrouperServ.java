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
import bo.union.platform.i16d.manager.data.Grouper;
import bo.union.platform.i16d.manager.filter.GrouperFtr;

/**
 * Interface Used to interact with the <code>Grouper</code> object.
 *
 * <p>
 * An instance of <code>GrouperServ</code> (as an EJB service or another container) is associated
 * with a persistence and independent transaction context. From this instance you can create /
 * modify / delete or list persistent instances of the <code>Grouper</code> object in the service.
 * </p>
 *
 * <p>
 * House one of the services responds to a functional and non-functional requirement of the
 * user-history.
 * </p>
 *
 * @see QueryFilter
 * @see ServiceException
 * @see ValidateException
 *
 * @since union-api
 *
 */
public interface GrouperServ {

	/**
	 * Search instances of the <code>Grouper</code> class from parent object.
	 *
	 * <p>
	 * Search and return instances of the <code>Grouper</code> object from the parent
	 * <code>Grouper</code> object.
	 * </p>
	 *
	 * @param parent
	 *         Instance of <code>Grouper</code>.
	 * @param level
	 *         Level recursivity allow search.
	 * @return Collections of <code>Grouper</code> Objects.
	 * @throws ServiceException
	 *          When the method applies integrity and process controls.
	 */
	public List<Grouper> childrenGrouper(Grouper parent, int level) throws ServiceException;

	/**
	 * Search instances of the <code>Grouper</code> class.
	 *
	 * <p>
	 * Search and return instances of the <code>Grouper</code> object filtered by the criteria declared
	 * in the <code>GrouperFtr</code> parameter.
	 * </p>
	 *
	 * @param filter
	 *         Instance of <code>GrouperFtr</code> that implements <code>FilterObject</code> with
	 *         values.
	 * @return Collections of <code>Grouper</code> Objects.
	 * @throws ServiceException
	 *          When the method applies integrity and process controls.
	 */
	public List<Grouper> filterGrouper(GrouperFtr filter) throws ServiceException;

	/**
	 * Insert the <code>Grouper</code> in the internal storage of the service.
	 *
	 * <p>
	 * Returns another instance of the <code>Grouper</code> object with complementary values.
	 * </p>
	 *
	 * @param value
	 *         Instance of <code>Grouper</code> with values.
	 * @return other instance of <code>Grouper</code> with complementary values.
	 * @throws ServiceException
	 *          When the method applies integrity and process controls.
	 */
	public Grouper createGrouper(Grouper value) throws ServiceException;

	/**
	 * Update the <code>Grouper</code> in the internal storage of the service.
	 *
	 * <p>
	 * Returns another instance of the <code>Grouper</code> object with complementary values.
	 * </p>
	 *
	 * @param value
	 *         Instance of <code>Grouper</code> with values.
	 * @return other instance of <code>Grouper</code> with complementary values.
	 * @throws ServiceException
	 *          When the method applies integrity and process controls.
	 */
	public Grouper updateGrouper(Grouper value) throws ServiceException;

	/**
	 * Removes the <code>Grouper</code> in the internal storage of the service.
	 *
	 * <p>
	 * Returns another instance of the <code>Grouper</code> object with complementary values.
	 * </p>
	 *
	 * @param value
	 *         Instance of <code>Grouper</code> with values.
	 * @return other instance of <code>Grouper</code> with complementary values.
	 * @throws ServiceException
	 *          When the method applies integrity and process controls.
	 */
	public Grouper removeGrouper(Grouper value) throws ServiceException;
}

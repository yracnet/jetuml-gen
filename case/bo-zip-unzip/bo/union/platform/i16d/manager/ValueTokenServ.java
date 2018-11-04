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
import bo.union.platform.i16d.manager.data.ValueToken;
import bo.union.platform.i16d.manager.filter.ValueTokenFtr;

/**
 * Interface Used to interact with the <code>ValueToken</code> object.
 *
 * <p>
 * An instance of <code>ValueTokenServ</code> (as an EJB service or another container) is associated
 * with a persistence and independent transaction context. From this instance you can create /
 * modify / delete or list persistent instances of the <code>ValueToken</code> object in the
 * service.
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
public interface ValueTokenServ {

	/**
	 * Search instances of the <code>ValueToken</code> class.
	 * 
	 * <p>
	 * Search and return instances of the <code>ValueToken</code> object filtered by the criteria
	 * declared in the <code>ValueTokenFtr</code> parameter.
	 * </p>
	 *
	 * @param filter
	 *         Instance of <code>ValueTokenFtr</code> that implements <code>FilterObject</code> with
	 *         values.
	 * @return Collections of <code>ValueToken</code> Objects.
	 * @throws ServiceException
	 *          When the method applies integrity and process controls.
	 */
	public List<ValueToken> filterValueToken(ValueTokenFtr filter) throws ServiceException;

	/**
	 * Insert the <code>ValueToken</code> in the internal storage of the service.
	 *
	 * <p>
	 * Returns another instance of the <code>ValueToken</code> object with complementary values.
	 * </p>
	 *
	 * @param value
	 *         Instance of <code>ValueToken</code> with values.
	 * @return other instance of <code>ValueToken</code> with complementary values.
	 * @throws ServiceException
	 *          When the method applies integrity and process controls.
	 */
	public ValueToken createValueToken(ValueToken value) throws ServiceException;

	/**
	 * Update the <code>ValueToken</code> in the internal storage of the service.
	 *
	 * <p>
	 * Returns another instance of the <code>ValueToken</code> object with complementary values.
	 * </p>
	 *
	 * @param value
	 *         Instance of <code>ValueToken</code> with values.
	 * @return other instance of <code>ValueToken</code> with complementary values.
	 * @throws ServiceException
	 *          When the method applies integrity and process controls.
	 */
	public ValueToken updateValueToken(ValueToken value) throws ServiceException;

	/**
	 * Update the <code>Value</code> of <code>ValueToken</code> in the internal storage of the service.
	 *
	 * <p>
	 * Returns another instance of the <code>ValueToken</code> object with complementary values.
	 * </p>
	 *
	 * @param value
	 *         Instance of <code>ValueToken</code> with values.
	 * @return other instance of <code>ValueToken</code> with complementary values.
	 * @throws ServiceException
	 *          When the method applies integrity and process controls.
	 */
	public ValueToken updateValue(ValueToken value) throws ServiceException;

	/**
	 * Removes the <code>ValueToken</code> in the internal storage of the service.
	 *
	 * <p>
	 * Returns another instance of the <code>ValueToken</code> object with complementary values.
	 * </p>
	 *
	 * @param value
	 *         Instance of <code>ValueToken</code> with values.
	 * @return other instance of <code>ValueToken</code> with complementary values.
	 * @throws ServiceException
	 *          When the method applies integrity and process controls.
	 */
	public ValueToken removeValueToken(ValueToken value) throws ServiceException;
}

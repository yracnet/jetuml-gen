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
import bo.union.platform.i16d.manager.data.ValueCertificate;
import bo.union.platform.i16d.manager.filter.ValueCertificateFtr;

/**
 * Interface Used to interact with the <code>ValueCertificate</code> object.
 *
 * <p>
 * An instance of <code>ValueCertificateServ</code> (as an EJB service or another container) is
 * associated with a persistence and independent transaction context. From this instance you can
 * create / modify / delete or list persistent instances of the <code>ValueCertificate</code> object
 * in the service.
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
public interface ValueCertificateServ {

	/**
	 * Search instances of the <code>ValueCertificate</code> class.
	 * 
	 * <p>
	 * Search and return instances of the <code>ValueCertificate</code> object filtered by the criteria
	 * declared in the <code>ValueCertificateFtr</code> parameter.
	 * </p>
	 *
	 * @param filter
	 *         Instance of <code>ValueCertificateFtr</code> that implements <code>FilterObject</code>
	 *         with values.
	 * @return Collections of <code>ValueCertificate</code> Objects.
	 * @throws ServiceException
	 *          When the method applies integrity and process controls.
	 */
	public List<ValueCertificate> filterValueCertificate(ValueCertificateFtr filter) throws ServiceException;

	/**
	 * Insert the <code>ValueCertificate</code> in the internal storage of the service.
	 *
	 * <p>
	 * Returns another instance of the <code>ValueCertificate</code> object with complementary values.
	 * </p>
	 *
	 * @param value
	 *         Instance of <code>ValueCertificate</code> with values.
	 * @return other instance of <code>ValueCertificate</code> with complementary values.
	 * @throws ServiceException
	 *          When the method applies integrity and process controls.
	 */
	public ValueCertificate createValueCertificate(ValueCertificate value) throws ServiceException;

	/**
	 * Update the <code>ValueCertificate</code> in the internal storage of the service.
	 *
	 * <p>
	 * Returns another instance of the <code>ValueCertificate</code> object with complementary values.
	 * </p>
	 *
	 * @param value
	 *         Instance of <code>ValueCertificate</code> with values.
	 * @return other instance of <code>ValueCertificate</code> with complementary values.
	 * @throws ServiceException
	 *          When the method applies integrity and process controls.
	 */
	public ValueCertificate updateValueCertificate(ValueCertificate value) throws ServiceException;

	/**
	 * Update the <code>Value</code> of <code>ValueCertificate</code> in the internal storage of the
	 * service.
	 *
	 * <p>
	 * Returns another instance of the <code>ValueCertificate</code> object with complementary values.
	 * </p>
	 *
	 * @param value
	 *         Instance of <code>ValueCertificate</code> with values.
	 * @return other instance of <code>ValueCertificate</code> with complementary values.
	 * @throws ServiceException
	 *          When the method applies integrity and process controls.
	 */
	public ValueCertificate updateValue(ValueCertificate value) throws ServiceException;

	/**
	 * Removes the <code>ValueCertificate</code> in the internal storage of the service.
	 *
	 * <p>
	 * Returns another instance of the <code>ValueCertificate</code> object with complementary values.
	 * </p>
	 *
	 * @param value
	 *         Instance of <code>ValueCertificate</code> with values.
	 * @return other instance of <code>ValueCertificate</code> with complementary values.
	 * @throws ServiceException
	 *          When the method applies integrity and process controls.
	 */
	public ValueCertificate removeValueCertificate(ValueCertificate value) throws ServiceException;
}

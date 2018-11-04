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
import bo.union.platform.i16d.manager.data.Bean;
import bo.union.platform.i16d.manager.data.BeanMethod;
import bo.union.platform.i16d.manager.data.Deployment;
import bo.union.platform.i16d.manager.data.Version;
import bo.union.platform.i16d.manager.filter.BeanFtr;
import bo.union.platform.i16d.manager.filter.BeanMethodFtr;
import bo.union.platform.i16d.manager.filter.VersionFtr;

/**
 * Interface Used to interact with the <code>Bean</code> object.
 *
 * <p>
 * An instance of <code>BeanServ</code> (as an EJB service or another container) is associated with
 * a persistence and independent transaction context. From this instance you can create / modify /
 * delete or list persistent instances of the <code>Bean</code> object in the service.
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
public interface BeanServ {

	public List<Version> filterVersion(VersionFtr filter) throws ServiceException;

	/**
	 * Search instances of the <code>Bean</code> class.
	 *
	 * <p>
	 * Search and return instances of the <code>Bean</code> object filtered by the criteria declared in
	 * the <code>BeanFtr</code> parameter.
	 * </p>
	 *
	 * @param filter
	 *         Instance of <code>BeanFtr</code> that implements <code>FilterObject</code> with values.
	 * @param deployment
	 *         Instance of <code>Deployment</code>
	 * @param version
	 *         Version of <code>Deployment</code> see <code>Version</code>
	 * @return Collections of <code>Bean</code> Objects.
	 * @throws ServiceException
	 *          When the method applies integrity and process controls.
	 */
	public List<Bean> filterBean(BeanFtr filter) throws ServiceException;

	/**
	 * Search instances of the <code>BeanMethod</code> class.
	 *
	 * <p>
	 * Search and return instances of the <code>BeanMethod</code> object filtered by the criteria
	 * declared in the <code>BeanMethodFtr</code> parameter.
	 * </p>
	 *
	 * @param filter
	 *         Instance of <code>BeanMethodFtr</code> that implements <code>FilterObject</code> with
	 *         values.
	 * @return Collections of <code>BeanMethod</code> Objects.
	 * @throws ServiceException
	 *          When the method applies integrity and process controls.
	 */
	public List<BeanMethod> filterBeanMethod(BeanMethodFtr filter) throws ServiceException;

	/**
	 * Update the <code>BeanMethod</code> in the internal storage of the service.
	 *
	 * <p>
	 * Returns another instance of the <code>BeanMethod</code> object with complementary values.
	 * </p>
	 *
	 * @param value
	 *         Instance of <code>BeanMethod</code> with values.
	 * @return other instance of <code>BeanMethod</code> with complementary values.
	 * @throws ServiceException
	 *          When the method applies integrity and process controls.
	 */
	public BeanMethod updateBeanMethod(BeanMethod value) throws ServiceException;

	public List<Deployment> getModule() throws ServiceException;
}

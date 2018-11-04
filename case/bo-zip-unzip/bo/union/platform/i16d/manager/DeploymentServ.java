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
import bo.union.platform.i16d.manager.data.Deployment;
import bo.union.platform.i16d.manager.data.Version;
import bo.union.platform.i16d.manager.filter.DeploymentFtr;

/**
 * Interface Used to interact with the <code>Deployment</code> object.
 *
 * <p>
 * An instance of <code>DeploymentServ</code> (as an EJB service or another container) is associated
 * with a persistence and independent transaction context. From this instance you can create /
 * modify / delete or list persistent instances of the <code>Deployment</code> object in the
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
public interface DeploymentServ {

	/**
	 * Search instances of the <code>Deployment</code> class.
	 * 
	 * <p>
	 * Search and return instances of the <code>Deployment</code> object filtered by the criteria
	 * declared in the <code>DeploymentFtr</code> parameter.
	 * </p>
	 *
	 * @param filter
	 *         Instance of <code>DeploymentFtr</code> that implements <code>FilterObject</code> with
	 *         values.
	 * @return Collections of <code>Deployment</code> Objects.
	 * @throws ServiceException
	 *          When the method applies integrity and process controls.
	 */
	public List<Deployment> filterDeployment(DeploymentFtr filter) throws ServiceException;

	/**
	 * Update the <code>Deployment</code> in the internal storage of the service.
	 *
	 * <p>
	 * Returns another instance of the <code>Deployment</code> object with complementary values.
	 * </p>
	 *
	 * @param value
	 *         Instance of <code>Deployment</code> with values.
	 * @return other instance of <code>Deployment</code> with complementary values.
	 * @throws ServiceException
	 *          When the method applies integrity and process controls.
	 */
	public Deployment updateDeployment(Deployment value) throws ServiceException;

	/**
	 * Search instances of the <code>Version</code> class.
	 * 
	 * <p>
	 * Search and return instances of the <code>Version</code> object filtered by the criteria declared
	 * in the <code>VersionFtr</code> parameter.
	 * </p>
	 *
	 * @param filter
	 *         Instance of <code>VersionFtr</code> that implements <code>FilterObject</code> with
	 *         values.
	 * @return Collections of <code>Version</code> Objects.
	 * @throws ServiceException
	 *          When the method applies integrity and process controls.
	 */
	public List<Version> filterVersion(Deployment value) throws ServiceException;
}
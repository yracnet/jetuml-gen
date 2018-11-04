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

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("rest")
public class RestConfig extends Application {

	private static final Set<Class<?>> classSet = new HashSet();
	static {
		classSet.add(ArchiveConfigRest.class);
		classSet.add(ArchiveTypeRest.class);
		classSet.add(ValueArchiveRest.class);
		classSet.add(ErrorRest.class);
		classSet.add(ValueUrlRest.class);
		classSet.add(ValueCertificateRest.class);
		classSet.add(ValueCredentialRest.class);
		classSet.add(ValueTokenRest.class);
		classSet.add(ValueRest.class);
		classSet.add(GrouperRest.class);
		classSet.add(ContentRest.class);
		classSet.add(DeploymentRest.class);
		classSet.add(BeanRest.class);
	}

	@Override
	public Set<Class<?>> getClasses() {
		return classSet;
	}
}

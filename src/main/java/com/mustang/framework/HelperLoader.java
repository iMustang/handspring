package com.mustang.framework;

import com.mustang.framework.helper.AopHelper;
import com.mustang.framework.helper.BeanHelper;
import com.mustang.framework.helper.ClassHelper;
import com.mustang.framework.helper.ControllerHelper;
import com.mustang.framework.helper.IocHelper;
import com.mustang.framework.util.ClassUtil;

/**
 * HelperLoader
 *
 * @author: xMustang
 * @since: 1.0
 */
public final class HelperLoader {
	public static void init() {
		Class<?>[] classList = {
				ClassHelper.class,
				BeanHelper.class,
				AopHelper.class,
				IocHelper.class,
				ControllerHelper.class
		};

		for (Class<?> cls : classList) {
			ClassUtil.loadClass(cls.getName());
		}
	}
}

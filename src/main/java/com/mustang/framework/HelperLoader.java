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
				ClassHelper.class, // 加载时会查找指定包下的所有类
				BeanHelper.class, // 加载时会加载带有@Controller、@Service的类
				AopHelper.class, // 加载时会解析继承AspectProxy，且有@Aspect注解的类，创建代理对象，并用代理对象替换原来对象。
				IocHelper.class, // 加载时解析@Autowired注解
				ControllerHelper.class // 加载时，搜索代码中@Controller注解类，并生成HandlerMap
		};

		for (Class<?> cls : classList) {
			ClassUtil.loadClass(cls.getName());
		}
	}
}

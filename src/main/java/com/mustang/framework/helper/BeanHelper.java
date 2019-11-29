package com.mustang.framework.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.mustang.framework.util.ReflectionUtil;

/**
 * BeanHelper
 * 也就是实现Bean容器
 *
 * @author: xMustang
 * @since: 1.0
 */
public final class BeanHelper {
	/**
	 * BEAN_MAP相当于一个Spring容器，拥有应用所有bean的实例，在本框架中是带@Controller、@Service的Bean
	 */
	private static final Map<Class<?>, Object> BEAN_MAP;

	static {
		BEAN_MAP = new HashMap<>();

		//获取应用中的所有bean（是带@Controller、@Service的Bean）
		Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();

		for (Class<?> beanClass : beanClassSet) {
			//将bean实例化，并放入bean容器中
			Object obj = ReflectionUtil.newInstance(beanClass);
			BEAN_MAP.put(beanClass, obj);
		}
	}

	public static Map<Class<?>, Object> getBeanMap() {
		return BEAN_MAP;
	}

	/**
	 * 获取 Bean 实例
	 *
	 * @param cls
	 * @param <T>
	 * @return
	 */
	public static <T> T getBean(Class<T> cls) {
		if (!BEAN_MAP.containsKey(cls)) {
			throw new RuntimeException("can not get bean by class: " + cls);
		}
		return (T) BEAN_MAP.get(cls);
	}

	/**
	 * 设置 Bean 实例
	 *
	 * @param cls
	 * @param obj
	 */
	public static void setBean(Class<?> cls, Object obj) {
		BEAN_MAP.put(cls, obj);
	}
}

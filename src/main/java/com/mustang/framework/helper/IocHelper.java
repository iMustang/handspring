package com.mustang.framework.helper;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;

import com.mustang.framework.annotation.Autowired;
import com.mustang.framework.util.ReflectionUtil;

/**
 * IocHelper
 * 为所有带 @Autowired 注解的属性注入实例，也就是实现IOC
 *
 * @author: xMustang
 * @since: 1.0
 */
public final class IocHelper {
	static {
		Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();

		if (MapUtils.isNotEmpty(beanMap)) {
			//遍历bean容器里的所有bean
			for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
				//bean的class类
				Class<?> beanClass = beanEntry.getKey();

				//bean的实例
				Object beanInstance = beanEntry.getValue();

				Field[] beanFields = beanClass.getDeclaredFields();
				if (ArrayUtils.isNotEmpty(beanFields)) {
					for (Field beanField : beanFields) {
						if (beanField.isAnnotationPresent(Autowired.class)) {
							Class<?> beanFieldClass = beanField.getType();

							// 此处只实现注入时变量声明类型是接口的情况
							beanFieldClass = findImplementClass(beanFieldClass);

							Object beanFieldInstance = beanMap.get(beanFieldClass);
							if (beanFieldInstance != null) {
								ReflectionUtil.setField(beanInstance, beanField, beanFieldInstance);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 获取接口对应的实现类
	 *
	 * @param interfaceClass
	 * @return
	 */
	private static Class<?> findImplementClass(Class<?> interfaceClass) {
		Class<?> implementClass = interfaceClass;

		// 接口对应的所有实现类
		Set<Class<?>> classSetSuper = ClassHelper.getClassSetBySuper(interfaceClass);
		if (CollectionUtils.isNotEmpty(classSetSuper)) {
			// 获取第一个实现类
			implementClass = classSetSuper.iterator().next();
		}
		return implementClass;
	}
}

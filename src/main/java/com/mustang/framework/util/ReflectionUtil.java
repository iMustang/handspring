package com.mustang.framework.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import lombok.extern.slf4j.Slf4j;

/**
 * ReflectionUtil
 *
 * @author: xMustang
 * @since: 1.0
 */
@Slf4j
public final class ReflectionUtil {
	/**
	 * 创建实例（根据类对象）
	 *
	 * @param cls
	 * @return
	 */
	public static Object newInstance(Class<?> cls) {
		Object instance;
		try {
			instance = cls.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			log.error("new instance failure", e);
			throw new RuntimeException(e);
		}
		return instance;
	}

	/**
	 * 创建实例（根据类名）
	 *
	 * @param className
	 * @return
	 */
	public static Object newInstance(String className) {
		Class<?> cls = ClassUtil.loadClass(className);
		return newInstance(cls);
	}

	/**
	 * 调用方法
	 *
	 * @param obj
	 * @param method
	 * @param args
	 * @return
	 */
	public static Object invokeMethod(Object obj, Method method, Object... args) {
		Object result;
		method.setAccessible(true);
		try {
			result = method.invoke(obj, args);
		} catch (IllegalAccessException | InvocationTargetException e) {
			log.error("invoke method failure", e);
			throw new RuntimeException(e);
		}
		return result;
	}

	/**
	 * 设置成员变量值
	 *
	 * @param obj
	 * @param field
	 * @param value
	 */
	public static void setField(Object obj, Field field, Object value) {
		field.setAccessible(true);
		try {
			field.set(obj, value);
		} catch (IllegalAccessException e) {
			log.error("set field failure", e);
			throw new RuntimeException(e);
		}
	}
}

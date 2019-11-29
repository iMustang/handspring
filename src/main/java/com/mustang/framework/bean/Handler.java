package com.mustang.framework.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Method;

/**
 * Handler
 * Handler类为一个处理器，封装了Controller的Class对象和Method方法
 *
 * @author: xMustang
 * @since: 1.0
 */
@AllArgsConstructor
@Getter
public class Handler {
	/**
	 * Controller类
	 */
	private Class<?> controllerClazz;
	/**
	 * Controller方法
	 */
	private Method controllerMethod;
}

package com.mustang.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.mustang.framework.constant.RequestMethod;

/**
 * RequestMapping
 *
 * @author: xMustang
 * @since: 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {
	/**
	 * 请求路径
	 *
	 * @return
	 */
	String value() default "";

	/**
	 * 请求方法
	 *
	 * @return
	 */
	RequestMethod method() default RequestMethod.GET;
}

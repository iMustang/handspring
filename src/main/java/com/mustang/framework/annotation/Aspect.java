package com.mustang.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Aspect
 * 切面注解
 *
 * @author: xMustang
 * @since: 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
	/**
	 * 包名
	 *
	 * @return
	 */
	String pkg() default "";

	/**
	 * 类名
	 *
	 * @return
	 */
	String cls() default "";
}

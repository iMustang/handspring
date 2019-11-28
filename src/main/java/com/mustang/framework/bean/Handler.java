package com.mustang.framework.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Method;

/**
 * Handler
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
    private Class<?> controllerClass;
    /**
     * Controller方法
     */
    private Method controllerMethod;
}

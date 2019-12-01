package com.mustang.anotherkind.framework.easy.aop;

import java.lang.reflect.Proxy;

/**
 * SimpleAop
 *
 * @author: xMustang
 * @since: 1.0
 */
public class SimpleAop {
    public static Object getProxy(Object bean, Advice advice) {
        return Proxy.newProxyInstance(
                bean.getClass().getClassLoader(),
                bean.getClass().getInterfaces(),
                advice);
    }
}

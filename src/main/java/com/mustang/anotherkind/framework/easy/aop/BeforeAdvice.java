package com.mustang.anotherkind.framework.easy.aop;

import lombok.AllArgsConstructor;

import java.lang.reflect.Method;

/**
 * BeforeAdvice
 *
 * @author: xMustang
 * @since: 1.0
 */
@AllArgsConstructor
public class BeforeAdvice implements Advice {
    private Object bean;
    private MethodInvocation methodInvocation;

    @Override
    public Object invoke(Object proxy,
                         Method method, Object[] args) throws Throwable {
        methodInvocation.invoke();
        return method.invoke(bean, args);
    }
}

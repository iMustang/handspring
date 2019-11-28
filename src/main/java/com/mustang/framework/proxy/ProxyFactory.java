package com.mustang.framework.proxy;

import java.lang.reflect.Method;
import java.util.List;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * ProxyFactory
 *
 * @author: xMustang
 * @since: 1.0
 */
public class ProxyFactory {
	public static <T> T createProxy(final Class<?> targetClass, final List<Proxy> proxyList) {
		return (T) Enhancer.create(targetClass, new MethodInterceptor() {
			@Override
			public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
				return new ProxyChain(targetClass, o,
						method, methodProxy, objects, proxyList
				);
			}
		});
	}
}

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
	/**
	 * 输入一个目标类，一组Proxy接口实现，得到一个代理对象
	 *
	 * @param targetClass
	 * @param proxyList
	 * @param <T>
	 * @return
	 */
	public static <T> T createProxy(final Class<?> targetClass, final List<Proxy> proxyList) {
		return (T) Enhancer.create(targetClass, new MethodInterceptor() {
			/**
			 * 代理方法，每次调用目标方法时都会先创建一个 ProxyChain 对象，然后调用该对象的 doProxyChain() 方法
			 * @param o
			 * @param method
			 * @param objects
			 * @param methodProxy
			 * @return
			 * @throws Throwable
			 */
			@Override
			public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
				return new ProxyChain(targetClass, o,
						method, methodProxy, objects, proxyList
				);
			}
		});
	}
}

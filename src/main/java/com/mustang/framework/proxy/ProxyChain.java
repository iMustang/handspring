package com.mustang.framework.proxy;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * ProxyChain
 *
 * @author: xMustang
 * @since: 1.0
 */
public class ProxyChain {
	private final Class<?> targetClass;// 目标类
	private final Object targetObject;// 目标对象
	private final Method targetMethod;// 目标方法
	private final MethodProxy methodProxy;// 方法代理
	private final Object[] methodParams;// 方法参数

	private List<Proxy> proxyList;// 代理列表，当执行doProxyChain() 方法时会按照顺序执行增强，最后再执行目标方法
	private int proxyIndex = 0;// 代理索引

	public ProxyChain(Class<?> targetClass, Object targetObject,
	                  Method targetMethod, MethodProxy methodProxy,
	                  Object[] methodParams, List<Proxy> proxyList) {
		this.targetClass = targetClass;
		this.targetObject = targetObject;
		this.targetMethod = targetMethod;
		this.methodProxy = methodProxy;
		this.methodParams = methodParams;
		this.proxyList = proxyList;
	}

	public Class<?> getTargetClass() {
		return targetClass;
	}

	public Method getTargetMethod() {
		return targetMethod;
	}

	public Object[] getMethodParams() {
		return methodParams;
	}

	/**
	 * 递归执行代理链上代理对象的增强方法，最后再执行目标对象的方法。
	 *
	 * @return
	 * @throws Throwable
	 */
	public Object doProxyChain() throws Throwable {
		Object methodResult;
		if (proxyIndex < proxyList.size()) {
			// 执行增强方法
			methodResult = proxyList.get(proxyIndex++).doProxy(this);
		} else {
			// 目标方法最后执行且只执行一次
			methodResult = methodProxy.invokeSuper(targetObject, methodParams);
		}
		return methodResult;
	}
}

package com.mustang.framework.proxy;

import java.lang.reflect.Method;

import lombok.extern.slf4j.Slf4j;

/**
 * AspectProxy
 * <p>
 * 抽象切面类，切面类继承该抽象切面类，并添加@Aspect
 * 类中定义了切入点判断和各种增强
 *
 * @author: xMustang
 * @since: 1.0
 */
@Slf4j
public class AspectProxy implements Proxy {
	@Override
	//当执行 doProxy() 方法时，会先进行切入点判断，再执行前置增强，代理链的下一个doProxyChain()方法，后置增强等。
	public Object doProxy(ProxyChain proxyChain) throws Throwable {
		Object result;

		Class<?> cls = proxyChain.getTargetClass();
		Method method = proxyChain.getTargetMethod();
		Object[] params = proxyChain.getMethodParams();

		begin();
		try {
			if (intercept(method, params)) {
				before(method, params);
				result = proxyChain.doProxyChain();
				after(method, params);
			} else {
				result = proxyChain.doProxyChain();
			}
		} catch (Exception e) {
			log.error("proxy failure", e);
			error(method, params, e);
			throw e;
		} finally {
			end();
		}

		return result;
	}

	/**
	 * 切入点判断
	 *
	 * @param method
	 * @param params
	 * @return
	 * @throws Throwable
	 */
	public boolean intercept(Method method, Object[] params) throws Throwable {
		return true;
	}

	/**
	 * 开始增强
	 */
	public void begin() {

	}

	/**
	 * 前置增强
	 *
	 * @param method
	 * @param params
	 * @throws Throwable
	 */
	public void before(Method method, Object[] params) throws Throwable {

	}

	/**
	 * 后置增强
	 *
	 * @param method
	 * @param params
	 * @throws Throwable
	 */
	public void after(Method method, Object[] params) throws Throwable {

	}

	/**
	 * 异常增强
	 *
	 * @param method
	 * @param params
	 * @param e
	 */
	public void error(Method method, Object[] params, Throwable e) {

	}

	/**
	 * 最终增强
	 */
	public void end() {

	}
}

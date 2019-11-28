package com.mustang.framework.proxy;

import java.lang.reflect.Method;

import lombok.extern.slf4j.Slf4j;

/**
 * AspectProxy
 *
 * @author: xMustang
 * @since: 1.0
 */
@Slf4j
public class AspectProxy implements Proxy {
	@Override
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
	 * 开始增强
	 */
	public void begin() {

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

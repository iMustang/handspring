package com.mustang.framework.proxy;

import java.lang.reflect.Method;

import com.mustang.framework.annotation.Transactional;
import com.mustang.framework.helper.DatabaseHelper;

import lombok.extern.slf4j.Slf4j;

/**
 * TransactionProxy
 *
 * @author: xMustang
 * @since: 1.0
 */
@Slf4j
public class TransactionProxy implements Proxy {
	@Override
	public Object doProxy(ProxyChain proxyChain) throws Throwable {
		Object result;
		Method method = proxyChain.getTargetMethod();
		//加了@Transactional注解的方法要做事务处理
		if (method.isAnnotationPresent(Transactional.class)) {
			try {
				DatabaseHelper.beginTransaction();
				log.debug("begin transaction");
				result = proxyChain.doProxyChain();
				DatabaseHelper.commitTransaction();
				log.debug("commit transaction");
			} catch (Exception e) {
				DatabaseHelper.rollbackTransaction();
				log.debug("rollback transaction");
				throw e;
			}
		} else {
			result = proxyChain.doProxyChain();
		}
		return result;
	}
}

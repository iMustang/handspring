package com.mustang.framework.proxy;

import java.lang.reflect.Method;

import com.mustang.framework.annotation.Transactional;
import com.mustang.framework.helper.DatabaseHelper;

import lombok.extern.slf4j.Slf4j;

/**
 * TransactionProxy
 * 事务用AOP实现的思路就是，前置增强为开启事务，后置增强为提交事务，异常增强为回滚事务。
 *
 * @author: xMustang
 * @since: 1.0
 */
@Slf4j
public class TransactionProxy implements Proxy {
	@Override
	// 在该事务代理中，默认所有@Service注解的对象都被代理了，我们在执行代理方法时判断目标方法上是否存在@Transactional,
	// 有@Transactional就加上事务管理，没有就直接执行

	// doProxy() 方法就是先判断代理方法上有没有 @Transactional 注解，如果有就加上事务管理，没有就直接执行。
	public Object doProxy(ProxyChain proxyChain) throws Throwable {
		Object result;
		Method method = proxyChain.getTargetMethod();
		//加了@Transactional注解的方法要做事务处理
		if (method.isAnnotationPresent(Transactional.class)) {
			try {
				// 前置增强为开启事务
				DatabaseHelper.beginTransaction();
				log.debug("begin transaction");
				result = proxyChain.doProxyChain();

				// 后置增强为提交事务
				DatabaseHelper.commitTransaction();
				log.debug("commit transaction");
			} catch (Exception e) {
				// 异常增强为回滚事务
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

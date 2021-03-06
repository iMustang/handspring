package com.mustang.framework.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mustang.framework.annotation.Aspect;
import com.mustang.framework.annotation.Service;
import com.mustang.framework.proxy.AspectProxy;
import com.mustang.framework.proxy.Proxy;
import com.mustang.framework.proxy.ProxyFactory;
import com.mustang.framework.proxy.TransactionProxy;
import com.mustang.framework.util.ClassUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * AopHelper
 * 用来初始化整个AOP框架
 * <p>
 * 初始化AOP框架实际上就是用代理对象覆盖掉Bean容器中的目标对象，这样根据目标类的Class对象从Bean容器中获取到的就是代理对象，从而达到了对目标对象增强的目的。
 *
 * @author: xMustang
 * @since: 1.0
 */
@Slf4j
public final class AopHelper {
	static {
		try {
			//切面类-目标类集合的映射
			Map<Class<?>, Set<Class<?>>> aspectMap = createAspectMap();
			//目标类-切面对象列表的映射
			Map<Class<?>, List<Proxy>> targetMap = createTargetMap(aspectMap);

			//把切面对象织入到目标类中，创建代理对象
			for (Map.Entry<Class<?>, List<Proxy>> targetEntry : targetMap.entrySet()) {
				Class<?> targetClass = targetEntry.getKey();
				List<Proxy> proxyList = targetEntry.getValue();
				Object proxy = ProxyFactory.createProxy(targetClass, proxyList);

				//覆盖Bean容器里目标类对应的实例，下次从Bean容器获取的就是代理对象了
				BeanHelper.setBean(targetClass, proxy);
			}
		} catch (Exception e) {
			log.error("aop failure", e);
		}
	}

	/**
	 * 获取切面类-目标类集合的映射，Map<切面类,Set<目标类>>
	 *
	 * @return
	 * @throws Exception
	 */
	private static Map<Class<?>, Set<Class<?>>> createAspectMap() throws Exception {
		HashMap<Class<?>, Set<Class<?>>> aspectMap = new HashMap<>();
		addAspectProxy(aspectMap);
		addTransactionProxy(aspectMap);
		return aspectMap;
	}

	/**
	 * 获取切面类-目标类集合的映射，Map<切面类, Set<目标类>>
	 *
	 * @param aspectMap
	 * @throws Exception
	 */
	private static void addAspectProxy(Map<Class<?>, Set<Class<?>>> aspectMap) throws Exception {
		// 所有实现了AspectProxy抽象类的切面
		Set<Class<?>> aspectClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);
		for (Class<?> aspectClass : aspectClassSet) {
			if (aspectClass.isAnnotationPresent(Aspect.class)) {
				Aspect aspect = aspectClass.getAnnotation(Aspect.class);

				// 与该切面对应的目标类集合
				// 如@Aspect(pkg = "com.xmustang.controller", cls = "UserController")，目标类是com.xmustang.controller
				// .UserController
				Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
				aspectMap.put(aspectClass, targetClassSet);
			}
		}
	}

	/**
	 * 获取事务切面类-目标类集合的映射，Map<事务切面, 目标类>
	 * <p>
	 * 该框架默认所有@Service注解类都被代理了
	 *
	 * @param aspectMap
	 */
	private static void addTransactionProxy(Map<Class<?>, Set<Class<?>>> aspectMap) {
		Set<Class<?>> serviceClassSet = ClassHelper.getClassSetByAnnotation(Service.class);
		aspectMap.put(TransactionProxy.class, serviceClassSet);
	}

	/**
	 * 根据@Aspect定义的包名和类名去获取对应的目标类集合，Set<目标类>
	 *
	 * @param aspect
	 * @return
	 * @throws Exception
	 */
	private static Set<Class<?>> createTargetClassSet(Aspect aspect) throws Exception {
		Set<Class<?>> targetClassSet = new HashSet<>();
		// 包名
		String pkg = aspect.pkg();
		// 类名
		String cls = aspect.cls();

		// 如果包名与类名均不为空，则添加指定类
		if (!"".equals(pkg) && !"".equals(cls)) {
			targetClassSet.add(Class.forName(pkg + "." + cls));
		} else if (!"".equals(pkg)) {
			// 如果包名不为空，类名为空，则添加该包名下所有类
			targetClassSet.addAll(ClassUtil.getClassSet(pkg));
		}
		return targetClassSet;
	}

	/**
	 * 将切面类-目标类集合的映射关系（Map<切面类,Set<目标类>>） 转化为 目标类-切面对象列表的映射关系，即Map<目标类,List<切面类实例>>
	 *
	 * @param aspectMap
	 * @return
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>,
			Set<Class<?>>> aspectMap) throws IllegalAccessException, InstantiationException {
		Map<Class<?>, List<Proxy>> targetMap = new HashMap<>();
		for (Map.Entry<Class<?>, Set<Class<?>>> proxyEntry : aspectMap.entrySet()) {
			//切面类
			Class<?> aspectClass = proxyEntry.getKey();
			//目标类集合
			Set<Class<?>> targetClassSet = proxyEntry.getValue();
			for (Class<?> targetClass : targetClassSet) {
				//切面对象
				Proxy aspect = (Proxy) aspectClass.newInstance();
				if (targetMap.containsKey(targetClass)) {
					targetMap.get(targetClass).add(aspect);
				} else {
					// 切面对象列表
					List<Proxy> aspectList = new ArrayList<>();
					aspectList.add(aspect);
					targetMap.put(targetClass, aspectList);
				}
			}
		}
		return targetMap;
	}
}

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

    private List<Proxy> proxyList = new ArrayList<>();// 代理列表
    private int proxyIndex = 0;// 代理索引
}

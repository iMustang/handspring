package com.mustang.framework.proxy;

/**
 * Proxy
 *
 * @author: xMustang
 * @since: 1.0
 */
public interface Proxy {
    /**
     * 执行链式代理
     * 将多个代理通过一条链串起来, 一个个地去执行, 执行顺序取决于添加到链上的先后顺序
     *
     * @param proxyChain
     * @return
     * @throws Throwable
     */
    Object doProxy(ProxyChain proxyChain) throws Throwable;
}

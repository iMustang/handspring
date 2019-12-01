package com.mustang.anotherkind.framework.easy.aop.test;

/**
 * HelloServiceImpl
 *
 * @author: xMustang
 * @since: 1.0
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public void sayHello() {
        System.out.println("hello world!");
    }
}

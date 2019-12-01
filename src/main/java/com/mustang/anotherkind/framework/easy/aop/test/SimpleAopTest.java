package com.mustang.anotherkind.framework.easy.aop.test;

import com.mustang.anotherkind.framework.easy.aop.Advice;
import com.mustang.anotherkind.framework.easy.aop.BeforeAdvice;
import com.mustang.anotherkind.framework.easy.aop.MethodInvocation;
import com.mustang.anotherkind.framework.easy.aop.SimpleAop;

/**
 * SimpleAopTest
 *
 * @author: xMustang
 * @since: 1.0
 */
public class SimpleAopTest {
    public static void main(String[] args) {
        // 1. 创建一个 MethodInvocation 实现类
        MethodInvocation logTask = new MethodInvocation() {
            @Override
            public void invoke() {
                System.out.println("log task start");
            }
        };

        HelloServiceImpl helloService = new HelloServiceImpl();

        // 2. 创建一个 Advice，这里传入目标对象是jdk动态代理InvocationHandler接口中invoke方法需要的
        Advice beforeAdvice = new BeforeAdvice(helloService, logTask);

        // 3. 为目标对象生成代理，目标对象是helloService，这里传入helloService只是为了获取目标对象实现的接口。
        HelloService helloServiceProxy = (HelloService) SimpleAop.getProxy(helloService, beforeAdvice);

        helloServiceProxy.sayHello();
    }
}

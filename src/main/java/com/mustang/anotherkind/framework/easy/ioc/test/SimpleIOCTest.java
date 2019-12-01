package com.mustang.anotherkind.framework.easy.ioc.test;

import com.mustang.anotherkind.framework.easy.ioc.SimpleIOC;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * SimpleIOCTest
 *
 * @author: xMustang
 * @since: 1.0
 */
public class SimpleIOCTest {
    public static void main(String[] args) throws IllegalAccessException, ParserConfigurationException, IOException, InstantiationException, SAXException, NoSuchFieldException {
        String file = SimpleIOC.class.getClassLoader().getResource("./ioc.xml").getFile();
        SimpleIOC beanFactory = new SimpleIOC(file);

        Wheel wheel = (Wheel) beanFactory.getBean("wheel");
        System.out.println(wheel);

        Car car = (Car) beanFactory.getBean("car");
        System.out.println(car);
    }
}

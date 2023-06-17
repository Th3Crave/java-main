package com.feng.base.proxy.dynamic.jdk.demo1;

/**
 * 实际对象
 */
public class RealSubject implements Subject {

    @Override
    public String sayHello(String name) {
        return "hello " + name;
    }

    @Override
    public String sayGoodBye() {
        return "good bye";
    }
}

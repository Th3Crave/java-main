package com.feng.base.proxy.dynamic.jdk.demo1;

/**
 * 需要动态代理的接口
 */
public interface Subject {


    /**
     * 你好
     * @param name
     * @return
     */
    String sayHello(String name);


    /**
     * 拜拜
     * @return
     */
    String sayGoodBye();
}

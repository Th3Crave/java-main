package com.feng.base.proxy.dynamic.jdk.demo2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class UserJDKProxy2 {

    /**
     * @param realObject 被代理的真实对象
     * @param clazz
     */
    public static <T> T getProxy(Object realObject, Class clazz) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        return (T) Proxy.newProxyInstance(classLoader, new Class[]{clazz}, new InvocationHandler() {

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 在代理真实对象前的操作
                System.out.println("在调用之前，干点啥呢？");

                System.out.println("Method:" + method);
                // 当代理对象调用真实对象的方法时，其会自动的跳转到代理对象关联的handler对象的invoke方法来进行调用
                Object returnValue = method.invoke(realObject, args);

                // 在代理真实对象后的操作
                System.out.println("在调用之后，干点啥呢？");

                System.out.println("[after] execute method: " + method.getName() + ", return value: " + returnValue);
                return returnValue;
            }
        });
    }
}

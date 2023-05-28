package com.feng.proxy.dynamic.cglib.demo1;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class UserCglibProxy implements MethodInterceptor {

//    public Object getCglibProxy(Object target) {
//        // 创建加强器，用来创建动态代理类
//        Enhancer enhancer = new Enhancer();
//        // 为加强器指定要代理的业务类（即为下面生成的代理类指定父类）
//        enhancer.setSuperclass(target.getClass());
//        // 设置回调：对于代理类上所有的方法的调用，都会调用callback，而callback则需要实现intercept()方法进行拦截
//        enhancer.setCallback(this);
//        // 创建代理类对象并返回
//        return enhancer.create();
//    }

    public Object getCglibProxy(Class<?> targetClass) {
        // 创建加强器，用来创建动态代理类
        Enhancer enhancer = new Enhancer();
        // 为加强器指定要代理的业务类（即为下面生成的代理类指定父类）
        enhancer.setSuperclass(targetClass);
        // 设置回调：对于代理类上所有的方法的调用，都会调用callback，而callback则需要实现intercept()方法进行拦截
        enhancer.setCallback(this);
        // 创建代理类对象并返回
        return enhancer.create();
    }

    /**
     * 实现回调方法
     */
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        // log - before method
        System.out.println("CglibProxy [before] execute method: " + method.getName());

        // call method
        Object result = proxy.invokeSuper(obj, args);

        // log - after method
        System.out.println("CglibProxy [after] execute method: " + method.getName() + ", return value: " + result);
        return null;
    }
}

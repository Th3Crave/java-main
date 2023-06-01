package com.feng.proxy.dynamic.jdk.demo1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;


/**
 * 调用处理器实现类
 * 每次生成动态代理对象时 都需要指定一个实现了该接口的调用处理器对象
 */
public class InvocationHandlerImpl implements InvocationHandler {

    /**
     * 要代理的真实对象
     */
    private Object subject;


    public InvocationHandlerImpl(Object subject) {
        this.subject = subject;
    }

    /**
     * 该方法负责集中处理动态代理类上的所有方法调用
     * 调用处理器根据这三个参数进行预处理或分派到委托类实例上反射执行
     *
     * @param proxy the proxy instance that the method was invoked on
     *
     * @param method the {@code Method} instance corresponding to
     * the interface method invoked on the proxy instance.  The declaring
     * class of the {@code Method} object will be the interface that
     * the method was declared in, which may be a superinterface of the
     * proxy interface that the proxy class inherits the method through.
     *
     * @param args an array of objects containing the values of the
     * arguments passed in the method invocation on the proxy instance,
     * or {@code null} if interface method takes no arguments.
     * Arguments of primitive types are wrapped in instances of the
     * appropriate primitive wrapper class, such as
     * {@code java.lang.Integer} or {@code java.lang.Boolean}.
     *
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        // 在代理真实对象前的操作
        System.out.println("在调用之前，干点啥呢？");

        System.out.println("Method:" + method);
        // 当代理对象调用真实对象的方法时，其会自动的跳转到代理对象关联的handler对象的invoke方法来进行调用
        Object returnValue = method.invoke(this.subject, args);

        // 在代理真实对象后的操作
        System.out.println("在调用之后，干点啥呢？");

        return returnValue;
    }
}

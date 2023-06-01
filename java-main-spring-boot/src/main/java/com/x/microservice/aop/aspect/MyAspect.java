package com.x.microservice.aop.aspect;

import com.x.microservice.aop.reflect.SuperClass;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Component
@Aspect
@Slf4j
public class MyAspect {


    @SneakyThrows
    @Before("@annotation(com.x.microservice.annotation.MyAnnotation)")
    public void before(JoinPoint joinPoint) {
        // before 只能用JoinPoint 而不能获取ProceedingJoinPoint

        Object param = null;
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof SuperClass) {
                param = arg;
                break;
            }
        }
        if (param == null) {
            return;
        }

        Class<?> clazz = param.getClass();
        // clazz.getFields() 类的public属性，包括自身以及父类的public属性
        log.info("clazz.getFields() length:{}", clazz.getFields().length);
        // clazz.getDeclaredFields() 类自身声明的属性，public、protected、private
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            log.info("clazz.getDeclaredFields() fieldName:{}, value:{}", field.getName(), field.get(param));
        }
        // clazz.getMethods() 类的public方法，包括自身以及父类的public方法
        for (Method method : clazz.getMethods()) {
            log.info("methodName:{}", method.getName());
        }
        // 通过父类的get方法获取父类的私有属性
        Method getSuperName = clazz.getMethod("getSuperName");
        String superName = (String) getSuperName.invoke(param);
        log.info("通过get方法获取父类的私有属性:{}", superName);
    }


    @Around("@annotation(com.x.microservice.annotation.MyAnnotation)")
    public void around(ProceedingJoinPoint point) throws Throwable {
        /**
         *   Proceedingjoinpoint 继承了JoinPoint，在JoinPoint的基础上暴露出 proceed()
         *   暴露出proceed()这个方法，就能支持 aop:around 这种切面
         *   而其他的几种切面只需要用到JoinPoint，这跟切面类型有关
         */

        log.info("MyAspect around before");

        point.proceed();

        log.info("MyAspect around after");
    }
}

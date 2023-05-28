package com.x.microservice.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class MyAspect {


    @Around("@annotation(com.x.microservice.annotation.MyAnnotation)")
    public void around(ProceedingJoinPoint point) throws Throwable {
        log.info("MyAspect around before");

        point.proceed();

        log.info("MyAspect around after");
    }
}

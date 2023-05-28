package com.x.microservice.aop.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class MyAopTestConfiguration {

    @Bean
    public MyTestClassA myTestClassA() {
        return new MyTestClassA();
    }

    @Bean
    public MyTestClassB myTestClassB() {
        MyTestClassA a1 = myTestClassA();
        MyTestClassA a2 = myTestClassA();

        // [main] INFO com.x.microservice.aop.test.MyAopTestConfiguration - MyAopTestConfiguration:true
        log.info("MyAopTestConfiguration:{}", a1 == a2);

        return new MyTestClassB();
    }
}

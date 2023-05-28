package com.x.microservice;

import com.x.microservice.service.TestCglibProxyService;
import com.x.microservice.service.TestJdkProxyService;
import com.x.microservice.service.TestJdkProxyServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


@org.springframework.boot.autoconfigure.SpringBootApplication
public class SpringBootApplication {
//    public static void main(String[] args) {
//        SpringApplication.run(SpringBootApplication.class, args);
//    }


    public static void main(String[] args) {
        ApplicationContext app = new AnnotationConfigApplicationContext(SpringBootApplication.class);

        TestCglibProxyService testCglibProxyService = app.getBean(TestCglibProxyService.class);
        testCglibProxyService.test();

        TestJdkProxyService testJdkProxyService = app.getBean(TestJdkProxyServiceImpl.class);
        testJdkProxyService.test();
    }
}

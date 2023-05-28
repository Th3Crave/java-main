package com.x.microservice;

import com.x.microservice.service.TestCglibProxyService;
import com.x.microservice.service.TestJdkProxyService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@org.springframework.boot.autoconfigure.SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@EnableAspectJAutoProxy
public class SpringBootApplication {
//    public static void main(String[] args) {
//        SpringApplication.run(SpringBootApplication.class, args);
//    }


    public static void main(String[] args) {
        ApplicationContext app = new AnnotationConfigApplicationContext(SpringBootApplication.class);

        TestCglibProxyService testCglibProxyService = app.getBean(TestCglibProxyService.class);
        testCglibProxyService.test();

        TestJdkProxyService testJdkProxyService = (TestJdkProxyService) app.getBean("testJdkProxyService");
        testJdkProxyService.test();
    }
}

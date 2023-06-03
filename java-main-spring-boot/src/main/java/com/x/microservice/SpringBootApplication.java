package com.x.microservice;

import com.x.microservice.aop.reflect.ChildClass;
import com.x.microservice.service.TestCglibProxyService;
import com.x.microservice.service.TestJdkProxyService;
import org.redisson.spring.starter.RedissonAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@org.springframework.boot.autoconfigure.SpringBootApplication
@ComponentScan("com.x.microservice")
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, RedissonAutoConfiguration.class, RedisAutoConfiguration.class})
@EnableAspectJAutoProxy
public class SpringBootApplication {
    public static void main1(String[] args) {
        SpringApplication.run(SpringBootApplication.class, args);
    }


    public static void main(String[] args) {

        ApplicationContext app = new AnnotationConfigApplicationContext(SpringBootApplication.class);
//        // reactive应用
//        ApplicationContext app = new AnnotationConfigReactiveWebServerApplicationContext(SpringBootApplication.class);
//        // servlet应用
//        ApplicationContext app = new AnnotationConfigServletWebServerApplicationContext(SpringBootApplication.class);


        TestCglibProxyService testCglibProxyService = app.getBean(TestCglibProxyService.class);
        // 测试反射获取父类的私有属性
        ChildClass childClass = new ChildClass();
        childClass.setChildName("child");
        childClass.setSuperName("super");
        testCglibProxyService.test(childClass);

        TestJdkProxyService testJdkProxyService = (TestJdkProxyService) app.getBean("testJdkProxyService");
        testJdkProxyService.test();
    }
}

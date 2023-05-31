package com.x.microservice.service;

import com.x.microservice.annotation.MyAnnotation;
import com.x.microservice.aop.reflect.ChildClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TestCglibProxyService {

    @MyAnnotation
    public void test(ChildClass childClass) {
        log.info("TestCglibProxyService test");
    }
}

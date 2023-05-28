package com.x.microservice.service;

import com.x.microservice.annotation.MyAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TestCglibProxyService {

    @MyAnnotation
    public void test() {
        log.info("TestCglibProxyService test");
    }
}

package com.x.microservice.service;

import com.x.microservice.annotation.MyAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("testJdkProxyService")
@Slf4j
public class TestJdkProxyServiceImpl implements TestJdkProxyService {

    @MyAnnotation
    @Override
    public void test() {
        log.info("TestJdkProxyServiceImpl test");
    }
}

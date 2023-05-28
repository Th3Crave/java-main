package com.x.microservice.controller;

import com.x.microservice.service.TestCglibProxyService;
import com.x.microservice.service.TestJdkProxyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private TestJdkProxyService testJdkProxyService;

    @Autowired
    private TestCglibProxyService testCglibProxyService;

    @RequestMapping("/test")
    public String test() {
        return "test ok";
    }
}

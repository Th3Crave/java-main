package com.x.microservice.feign;

import feign.InvocationContext;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.ResponseInterceptor;

import java.io.IOException;

public class BlogTechInterceptor implements RequestInterceptor, ResponseInterceptor {


    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("Authorization", "frozen-wind");
    }

    @Override
    public Object aroundDecode(InvocationContext invocationContext) throws IOException {
        return null;
    }
}

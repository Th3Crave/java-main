package com.haier.rdtp.microservice.service.feign.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

/**
 * feign拦截器
 * 需要在yml中指明属于哪个feignClient - ${feign.client.config.xxxx.requestInterceptors}
 *
 */
@Slf4j
public class DemoFeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("apiKey", GatewayApiKeyHelper.API_KEY);
        log.info("{}:{}", "DemoFeignInterceptor apiKey", requestTemplate.headers().get("apiKey"));
    }
}


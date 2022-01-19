package com.haier.rdtp.microservice.service.feign.interceptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 帮助类 -读取yml中的参数
 */
@Component
class GatewayApiKeyHelper {

    private GatewayApiKeyHelper() { }

    static String API_KEY;

    @Value("${feign.client.config.gateway-demo-client.Authorization}")
    public void setApiKey(String apiKey){
        API_KEY = apiKey;
    }

}

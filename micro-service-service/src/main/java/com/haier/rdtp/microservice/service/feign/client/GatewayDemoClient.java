package com.haier.rdtp.microservice.service.feign.client;

import com.haier.gdp.pangu.common.dto.GlobalResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 不引入 api jar 包的调用方式
 */
@FeignClient(name = "gateway-demo-client", url = "${feign.client.config.gateway-demo-client.url}")
public interface GatewayDemoClient {

    @GetMapping(value = "/api/v1/inventory")
    GlobalResponse<?> get(@RequestParam("productCode") String productCode);

}
package com.x.microservice.api.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 说明:
 *
 * 1. FeignClient 提供给其它服务直接使用的feignClient
 *     url   -提供方的服务地址, 调用方在配置文件中配置此项;
 *     name  -提供方的feign name, 需遵循命名规范, 以防重复;
 *
 * 2. 在feignClient中定义controller需要实现的方法, @RequestMapping也都在这里配置
 *
 */
@FeignClient(name = "xxxproject-xxxservice-api", url = "${feign.client.config.xxxproject-xxxservice-api.url}")
@RequestMapping("/demo")
public interface DemoClient {
}

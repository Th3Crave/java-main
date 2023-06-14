package com.x.microservice.feign;

import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * FeignClient(name/value = "provider")中的value值表示的是这个Feign客户端需要请求的微服务名称。
 * 这个名称代表了一个微服务或一个微服务组，其实就是spring.application.name的值。
 * Feign客户端通过这个别名去EurekaServer服务端去找到这个别名对应的微服务。
 * 如果你在项目中还使用了ribbon做负载均衡且结合了Eureka，那么，ribbon将去Eureka的服务注册中心拉取注册表，并缓存到本地，并结合负载均衡算法，选取一个物理主机作为服务端。
 */
@FeignClient(name = "blog-frozen-wind",
        url = "https://blog.frozenwind.online",
        configuration = {BlogTechConfig.class, BlogTechInterceptor.class},
        fallbackFactory = BlogTechFeignFallbackFactory.class)
public interface BlogTechFeign {

    @GetMapping("/categories/emotion/")
    Response categoriesEmotion();
}

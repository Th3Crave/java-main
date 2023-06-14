package com.x.microservice.feign;

import feign.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;

@Slf4j
public class BlogTechFeignFallbackFactory implements FallbackFactory<BlogTechFeign> {

    @Override
    public BlogTechFeign create(Throwable cause) {

        log.error("BlogTechFeign error:{}", cause.getMessage());

        return new BlogTechFeign() {
            @Override
            public Response categoriesEmotion() {
                return null;
            }
        };
    }
}

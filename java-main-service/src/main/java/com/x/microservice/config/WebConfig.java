package com.x.microservice.config;

import com.x.microservice.config.interceptor.AuthInterceptor;
import com.x.microservice.config.interceptor.SchedulerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 按拦截器的顺序依次往下执行
        // HandlerInterceptor的preHandle方法若返回true 继续执行下一个拦截器
        // 若为false 则停止

        registry.addInterceptor(new AuthInterceptor())
                .addPathPatterns("/**");
//                .excludePathPatterns("/**/i_scheduler/**");

        registry.addInterceptor(new SchedulerInterceptor())
                .addPathPatterns("/**");
    }
}

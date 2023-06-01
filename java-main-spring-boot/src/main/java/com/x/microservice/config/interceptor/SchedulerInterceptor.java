package com.x.microservice.config.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
@Slf4j
public class SchedulerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String servletPath = request.getServletPath();
        // 非任务请求，直接通过
        if (!servletPath.startsWith("/i_scheduler")) {
            log.info("SchedulerInterceptor not i_scheduler");
            return true;
        }

        String path = servletPath.replaceAll("/i_scheduler/", "");
        // 心跳探测
        if ("ping".equals(path)) {
            response.setStatus(200);
            log.info("SchedulerInterceptor ping");
            return false;
        }

        log.info("SchedulerInterceptor do i_scheduler");
        return false;
    }
}

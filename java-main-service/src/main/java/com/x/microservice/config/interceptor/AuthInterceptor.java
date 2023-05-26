package com.x.microservice.config.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userAccount = request.getHeader("X-USER");
        if (userAccount == null || userAccount.equals("")) {
            throw new Exception("user not login");
        }
        log.info("AuthInterceptor x-user:{}", userAccount);
        return true;
    }
}

package com.bestbenefits.takoyaki.interceptor;

import com.bestbenefits.takoyaki.config.properties.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthenticationCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){

        HttpSession session = request.getSession();
        Boolean authentication = (Boolean) session.getAttribute(SessionConst.AUTHENTICATION);

        if (authentication == null || !authentication)
            throw new IllegalStateException("you need login.");
        return true;
    }
}
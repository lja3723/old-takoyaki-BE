package com.bestbenefits.takoyaki.config;

import com.bestbenefits.takoyaki.config.annotation.SessionMethodArgumentResolver;
import com.bestbenefits.takoyaki.interceptor.AuthenticationCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthenticationCheckInterceptor())
                .addPathPatterns("/user/**")
                .excludePathPatterns(
                        "/js/**", "/oauth_example", "/oauth", "/favicon.ico", //실험용이니 나중에 삭제하기
                        "/user/login-check",
                        "/user/oauth/login-url/**",
                        "/user/oauth/login/**",
                        "/user/duplicate-nickname",
                        "/user/oauth/login/additional-info"
                );
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new SessionMethodArgumentResolver());
    }
}

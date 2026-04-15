package com.xichen.Config;

import com.xichen.Security.JwtAuthFilter;
import jakarta.annotation.Resource;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Web配置
 */
@Configuration
public class WebConfig {
    @Resource
    private JwtAuthFilter jwtAuthFilter;
    @Bean
    public FilterRegistrationBean<JwtAuthFilter> jwtFilter() {
        FilterRegistrationBean<JwtAuthFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(jwtAuthFilter);
        registration.addUrlPatterns("/*"); // 拦截路径
        registration.setOrder(1); // 执行顺序
        return registration;
    }
}

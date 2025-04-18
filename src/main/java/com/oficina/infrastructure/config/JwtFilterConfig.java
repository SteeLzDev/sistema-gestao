//package com.oficina.infrastructure.config;
//
//import com.oficina.infrastructure.security.JwtRequestFilter;
//import jakarta.servlet.FilterRegistration;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.core.Ordered;
//
//public class JwtFilterConfig {
//    private final JwtRequestFilter jwtRequestFilter;
//
//    @Autowired
//    public JwtFilterConfig(JwtRequestFilter jwtRequestFilter) {
//        this.jwtRequestFilter = jwtRequestFilter;
//    }
//
//    @Bean
//    public FilterRegistrationBean<JwtRequestFilter> jwtFilterRegistration() {
//        FilterRegistrationBean<JwtRequestFilter> registration = new FilterRegistrationBean<>(jwtRequestFilter);
//        registration.setOrder(Ordered.HIGHEST_PRECEDENCE + 10);
//        registration.addUrlPatterns("/api/*");
//        return registration;
//    }
//}

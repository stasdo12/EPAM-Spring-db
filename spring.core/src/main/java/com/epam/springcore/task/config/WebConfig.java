package com.epam.springcore.task.config;

import interceptor.RequestResponseLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
@ComponentScan("interceptor")
public class WebConfig implements WebMvcConfigurer {

    private final RequestResponseLogger restLoggingInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(restLoggingInterceptor);
    }
}
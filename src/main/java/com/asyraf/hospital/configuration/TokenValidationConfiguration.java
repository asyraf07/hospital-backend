package com.asyraf.hospital.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.asyraf.hospital.interceptor.TokenValidationInterceptor;

@Configuration
public class TokenValidationConfiguration implements WebMvcConfigurer {

    private final TokenValidationInterceptor tokenValidationInterceptor;

    @Autowired
    public TokenValidationConfiguration(TokenValidationInterceptor tokenValidationInterceptor) {
        this.tokenValidationInterceptor = tokenValidationInterceptor;
    }

    // @Override
    // public void addInterceptors(InterceptorRegistry registry) {
    //     registry.addInterceptor(tokenValidationInterceptor)
    //         .excludePathPatterns("/auth/*");
    // }
    
}

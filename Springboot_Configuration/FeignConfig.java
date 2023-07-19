package com.fourbench.schoolmanagementservice.configuration;

import feign.Request;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Bean
    public Request.Options options() {
        return new Request.Options(5000, 10000);
    }

    @Bean
    public Retryer retryer() {
        return new Retryer.Default(5000, 5000, 3);
    }
}
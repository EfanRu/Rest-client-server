package com.example.resttemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestTemplateConfig {
//    @Bean
//    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
//        return restTemplateBuilder
//                .setConnectTimeout(Duration.ofSeconds(2))
//                .setReadTimeout(Duration.ofSeconds(3))
//                .build();
//    }

//    @Bean
//    public RestOperations restTemplateBuilder(RestTemplateBuilder restTemplateBuilder) {
//        return restTemplateBuilder.basicAuthentication("admin", new BCryptPasswordEncoder().encode("admin")).build();
//    }

    @Autowired
    @Bean
    public RestTemplate getRestTemplate(RestTemplateBuilder builder) {
//        RestTemplate restTemplate = builder.basicAuthorization("admin", "qwe").build();
        return builder.basicAuthentication("admin", "admin").build();
    }
}

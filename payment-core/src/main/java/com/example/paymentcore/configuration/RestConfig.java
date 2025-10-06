package com.example.paymentcore.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestConfig {

    @Bean
    RestClient restClient() {
        return RestClient.builder()
                .baseUrl("http://localhost:8080/products")
                .build();
    }
}

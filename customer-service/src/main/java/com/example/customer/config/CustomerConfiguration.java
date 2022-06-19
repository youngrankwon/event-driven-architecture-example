package com.example.customer.config;

import com.example.customer.aync.CustomerProducer;
import com.example.customer.service.CustomerService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
public class CustomerConfiguration {
    @Bean
    public CustomerService customerService() {
        return new CustomerService();
    }

    @Bean
    public CustomerProducer customerProducer() {
        return new CustomerProducer();
    }
}

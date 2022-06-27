package com.example.order.config;

import com.example.order.aync.OrderProducer;
import com.example.order.service.OrderService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
public class OrderConfiguration {

    @Bean public OrderService orderService() {
        return new OrderService();
    }

    @Bean public OrderProducer orderProducer() {
        return new OrderProducer();
    }
}

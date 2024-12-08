package com.epam.springcore.task.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${exchange.rabbitMQ.name}")
    private String exchangeName;


    public DirectExchange directExchange(){
        return new DirectExchange(exchangeName);
    }
}

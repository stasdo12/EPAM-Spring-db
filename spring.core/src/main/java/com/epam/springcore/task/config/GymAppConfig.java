package com.epam.springcore.task.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;



@Configuration
@ComponentScan(basePackages = "com.epam.springcore.task")
@PropertySource("classpath:application.properties")
public class GymAppConfig {


}

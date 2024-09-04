package com.epam.springcore.task.config;


import com.epam.springcore.task.model.Trainee;
import com.epam.springcore.task.model.Trainer;
import com.epam.springcore.task.model.Training;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Configuration
@ComponentScan(basePackages = "com.epam.springcore.task")
@PropertySource("classpath:application.properties")
public class GymAppConfig {


    @Bean
    public Map<Long, Trainee> traineesStorage(){
        return new ConcurrentHashMap<>();
    }
    @Bean
    public Map<Long, Trainer> trainersStorage(){
        return new ConcurrentHashMap<>();
    }
    @Bean
    public Map<Long, Training> trainingsStorage(){
        return new ConcurrentHashMap<>();
    }

}

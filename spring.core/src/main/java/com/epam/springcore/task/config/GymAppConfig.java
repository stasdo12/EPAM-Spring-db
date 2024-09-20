package com.epam.springcore.task.config;

import com.epam.springcore.task.utils.impl.AuthenticationUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GymAppConfig {

    @Bean
    public AuthenticationUtils authenticationUtils(){
        return new AuthenticationUtils();
    }

}

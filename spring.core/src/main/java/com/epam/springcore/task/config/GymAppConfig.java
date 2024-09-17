package com.epam.springcore.task.config;

import com.epam.springcore.task.mapper.TraineeMapper;
import com.epam.springcore.task.mapper.TrainerMapper;
import com.epam.springcore.task.utils.impl.AuthenticationUtils;
import org.springframework.context.annotation.*;

import java.util.Map;

@Configuration
public class GymAppConfig {

    @Bean
    public TraineeMapper traineeMapper(){
        return TraineeMapper.INSTANCE;
    }

    @Bean
    public TrainerMapper trainerMapper(){
        return TrainerMapper.INSTANCE;
    }

    @Bean
    public AuthenticationUtils authenticationUtils(){
        return new AuthenticationUtils();
    }

}

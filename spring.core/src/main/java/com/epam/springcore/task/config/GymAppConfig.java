package com.epam.springcore.task.config;

import com.epam.springcore.task.model.Trainee;
import com.epam.springcore.task.model.Trainer;
import com.epam.springcore.task.model.Training;
import com.epam.springcore.task.storage.Storage;
import org.springframework.context.annotation.*;

import java.util.Map;

@Configuration
public class GymAppConfig {

    private final Storage storage;

    public GymAppConfig(Storage storage) {
        this.storage = storage;
    }

    @Bean
    @DependsOn("storageInitializer")
    public Map<Long, Trainee> traineesStorage(){
        return storage.getTrainees();    }

    @Bean
    @DependsOn("storageInitializer")
    public Map<Long, Trainer> trainersStorage(){
        return storage.getTrainers();
    }

    @Bean
    @DependsOn("storageInitializer")
    public Map<Long, Training> trainingsStorage(){
        return storage.getTrainings();
    }
}

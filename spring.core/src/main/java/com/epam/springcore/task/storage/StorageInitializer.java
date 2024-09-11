package com.epam.springcore.task.storage;


import com.epam.springcore.task.model.JsonDataContainer;
import com.epam.springcore.task.model.Trainee;
import com.epam.springcore.task.model.Trainer;
import com.epam.springcore.task.model.Training;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class StorageInitializer implements ApplicationListener<ApplicationReadyEvent> {

    @Value("${file.path.initialData}")
    private Resource resource;

    private final Storage storage;

    private static final Logger logger = LoggerFactory.getLogger(StorageInitializer.class);


    @Autowired
    public StorageInitializer(Storage storage) {
        this.storage = storage;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));

        try (InputStream inputStream = resource.getInputStream()) {
            JsonDataContainer dataContainer = objectMapper.readValue(inputStream, JsonDataContainer.class);

            Map<Long, Trainee> traineeMap = dataContainer.getTrainees().stream()
                    .collect(Collectors.toMap(Trainee::getTraineeId, Function.identity()));

            Map<Long, Trainer> trainerMap = dataContainer.getTrainers().stream()
                    .collect(Collectors.toMap(Trainer::getTrainerId, Function.identity()));

            Map<Long, Training> trainingMap = dataContainer.getTrainings().stream()
                    .collect(Collectors.toMap(Training::getTrainingId, Function.identity()));

            storage.setTrainees(traineeMap);
            storage.setTrainers(trainerMap);
            storage.setTrainings(trainingMap);

            logger.info("Storage has been initialized with data from file.");

        } catch (IOException e) {
            logger.error("Error initializing storage from file: {}", e.getMessage(), e);
        }
    }

}
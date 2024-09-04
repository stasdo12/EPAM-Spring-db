package com.epam.springcore.task.storage;


import com.epam.springcore.task.model.JsonDataContainer;
import com.epam.springcore.task.model.Trainee;
import com.epam.springcore.task.model.Trainer;
import com.epam.springcore.task.model.Training;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class StorageInitializer {

    @Value("${file.path.initialData}")
    private Resource resource;

    private final Storage storage;

    @Autowired
    public StorageInitializer(Storage storage) {
        this.storage = storage;
    }

    @PostConstruct
    public void init() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));

        try (InputStream inputStream = resource.getInputStream()) {
            JsonDataContainer dataContainer = objectMapper.readValue(inputStream, JsonDataContainer.class);

            storage.setTrainees(dataContainer.getTrainees().stream()
                    .collect(Collectors.toMap(Trainee::getTraineeId, Function.identity())));

            storage.setTrainers(dataContainer.getTrainers().stream()
                    .collect(Collectors.toMap(Trainer::getTrainerId, Function.identity())));

            storage.setTrainings(dataContainer.getTrainings().stream()
                    .collect(Collectors.toMap(Training::getTrainingId, Function.identity())));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
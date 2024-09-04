package com.epam.springcore.task.storage.impl;

import com.epam.springcore.task.model.Trainee;
import com.epam.springcore.task.model.Trainer;
import com.epam.springcore.task.model.Training;
import com.epam.springcore.task.storage.Storage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Component
public class MyMemoryStorage implements Storage {

    private Map<Long, Trainee> trainees = new HashMap<>();
    private Map<Long, Trainer> trainers = new HashMap<>();
    private Map<Long, Training> trainings = new HashMap<>();
}

package com.epam.springcore.task.storage;

import com.epam.springcore.task.model.Trainee;
import com.epam.springcore.task.model.Trainer;
import com.epam.springcore.task.model.Training;

import java.util.Map;

public interface Storage {
    Map<Long, Trainee> getTrainees();
    void setTrainees(Map<Long, Trainee> trainees);

    Map<Long, Trainer> getTrainers();
    void setTrainers(Map<Long, Trainer> trainers);

    Map<Long, Training> getTrainings();
    void setTrainings(Map<Long, Training> trainings);
}

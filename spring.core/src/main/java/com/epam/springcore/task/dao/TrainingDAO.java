package com.epam.springcore.task.dao;

import com.epam.springcore.task.model.Training;

import java.util.List;
import java.util.Optional;

public interface TrainingDAO {

    Optional<Training> create(long trainingId, Training training);
    Optional<Training> getById(long trainingId);
    List<Training> getAllTrainings();
    long getMaxId();
}

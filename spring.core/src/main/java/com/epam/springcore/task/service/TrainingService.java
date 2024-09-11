package com.epam.springcore.task.service;

import com.epam.springcore.task.model.Training;

import java.util.List;
import java.util.Optional;

public interface TrainingService {

    Optional<Training> create(Training training);

    Optional<Training> findById(long trainingId);

    List<Training> getAllTrainings();

}

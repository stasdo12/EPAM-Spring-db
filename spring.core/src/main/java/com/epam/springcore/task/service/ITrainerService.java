package com.epam.springcore.task.service;

import com.epam.springcore.task.model.Trainer;

import java.util.Optional;

public interface ITrainerService {

    Trainer saveTrainer(Trainer trainer);

    Optional<Trainer> findById(Long trainerId);

    Optional<Trainer> findByUserId(Long userId);

    void deleteTrainer(Long trainerId);

}

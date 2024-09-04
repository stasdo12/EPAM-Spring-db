package com.epam.springcore.task.service;

import com.epam.springcore.task.model.Trainer;

import java.util.List;
import java.util.Optional;

public interface TrainerService {
    Optional<Trainer> create (Trainer trainer);
    Optional<Trainer> update (Trainer trainer);
    Optional<Trainer> getById (long trainerId);
    Optional<Trainer> getByUsername(String username);
    List<Trainer> getAllTrainers();
}

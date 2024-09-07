package com.epam.springcore.task.dao;

import com.epam.springcore.task.model.Trainer;

import java.util.List;

import java.util.Optional;

public interface TrainerDAO {

    Optional<Trainer> create(long trainerId, Trainer trainer);

    Optional<Trainer> update (Trainer trainer);

    Optional<Trainer> getById(long trainerId);

    Optional<Trainer> getByUsername(String username);

    List<Trainer> getAllTrainers();

    List<Trainer> getAllTrainersByUsername(String username);

    long getMaxId();

}
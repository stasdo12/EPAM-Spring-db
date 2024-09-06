package com.epam.springcore.task.dao.impl;

import com.epam.springcore.task.dao.TrainerDAO;
import com.epam.springcore.task.model.Trainer;
import com.epam.springcore.task.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository
public class TrainerDAOImpl implements TrainerDAO {

    private static final Logger logger = LoggerFactory.getLogger(TrainerDAOImpl.class);


    private final Map<Long, Trainer> trainersStorage;

    public TrainerDAOImpl(Map<Long, Trainer> trainersStorage) {
        this.trainersStorage = trainersStorage;
    }


    @Override
    public Optional<Trainer> create(long trainerId, Trainer trainer) {
        logger.debug("Creating trainer with ID: {}", trainerId);
        trainersStorage.put(trainerId, trainer);
        Optional<Trainer> createdTrainer = getById(trainerId);
        if (createdTrainer.isPresent()) {
            logger.info("Successfully created trainer: {}", createdTrainer.get());
        } else {
            logger.warn("Failed to create trainer with ID: {}", trainerId);
        }
        return createdTrainer;
    }

    @Override
    public Optional<Trainer> update(Trainer trainer) {
        logger.debug("Updating trainer with ID: {}", trainer.getTrainerId());
        Optional<Trainer> oldTrainer = Optional.ofNullable(trainersStorage.replace(trainer.getTrainerId(), trainer));
        if (oldTrainer.isPresent()) {
            logger.info("Successfully updated trainer: {}", trainer);
        } else {
            logger.warn("Failed to update trainer with ID: {}", trainer.getTrainerId());
        }
        return oldTrainer;
    }

    @Override
    public Optional<Trainer> getById(long trainerId) {
        logger.debug("Finding trainer by ID: {}", trainerId);
        Optional<Trainer> trainer = Optional.ofNullable(trainersStorage.get(trainerId));
        if (trainer.isPresent()) {
            logger.info("Found trainer: {}", trainer.get());
        } else {
            logger.warn("No trainer found with ID: {}", trainerId);
        }
        return trainer;
    }

    @Override
    public Optional<Trainer> getByUsername(String username) {
        logger.debug("Finding trainer by username: {}", username);
        if (username == null) {
            logger.warn("Username is null");
            return Optional.empty();
        }
        Optional<Trainer> trainer = trainersStorage.values()
                .stream()
                .filter(trainer1 -> {
                    User user = trainer1.getUser();
                    return user != null && username.equals(user.getUserName());
                })
                .findAny();
        if (trainer.isPresent()) {
            logger.info("Found trainer: {}", trainer.get());
        } else {
            logger.warn("No trainer found with username: {}", username);
        }
        return trainer;
    }

    @Override
    public List<Trainer> getAllTrainers() {
        return new ArrayList<>(trainersStorage.values());
    }

    @Override
    public List<Trainer> getAllTrainersByUsername(String username) {
        logger.debug("Finding all trainers by username pattern: {}", username);
        if (username == null) {
            logger.warn("Username pattern is null");
            return Collections.emptyList();
        }
        List<Trainer> trainers = trainersStorage.values()
                .stream()
                .filter(trainer -> {
                    User user = trainer.getUser();
                    return user != null && user.getUserName() != null && user.getUserName().matches(username + ".*");
                })
                .collect(Collectors.toList());
        logger.info("Found {} trainers with username pattern: {}", trainers.size(), username);
        return trainers;
    }

    @Override
    public long getMaxId() {
        return trainersStorage.keySet().stream().max(Long::compareTo).orElse(0L) + 1;
    }
}

package com.epam.springcore.task.dao.impl;


import com.epam.springcore.task.dao.TraineeDAO;
import com.epam.springcore.task.model.Trainee;
import com.epam.springcore.task.model.User;
import com.epam.springcore.task.service.impl.TrainerServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class TraineeDAOImpl implements TraineeDAO {

    private static final Logger logger = LoggerFactory.getLogger(TrainerServiceImpl.class);

    private final Map<Long, Trainee> traineesStorage;


    public TraineeDAOImpl(Map<Long, Trainee> traineesStorage) {
        this.traineesStorage = traineesStorage;
    }

    @Override
    public Optional<Trainee> create(long traineeId, Trainee trainee) {
        logger.debug("Creating trainee with ID: {}", traineeId);
        traineesStorage.put(traineeId, trainee);
        Optional<Trainee> oldTrainee = Optional.ofNullable(traineesStorage.replace(trainee.getTraineeId(), trainee));
        if (oldTrainee.isPresent()) {
            logger.info("Successfully updated trainee: {}", trainee);
        } else {
            logger.warn("Failed to update trainee with ID: {}", trainee.getTraineeId());
        }
        return oldTrainee;
    }

    @Override
    public Optional<Trainee> update(Trainee trainee) {
        logger.debug("Updating trainee with ID: {}", trainee.getTraineeId());
        Optional<Trainee> oldTrainee = Optional.ofNullable(traineesStorage.replace(trainee.getTraineeId(), trainee));
        if (oldTrainee.isPresent()) {
            logger.info("Successfully updated trainee: {}", trainee);
        } else {
            logger.warn("Failed to update trainee with ID: {}", trainee.getTraineeId());
        }
        return oldTrainee;
    }

    @Override
    public boolean deleteById(long traineeId) {
        logger.debug("Deleting trainee with ID: {}", traineeId);
        boolean isDeleted = traineesStorage.remove(traineeId) != null;
        if (isDeleted) {
            logger.info("Successfully deleted trainee with ID: {}", traineeId);
        } else {
            logger.warn("Failed to delete trainee with ID: {}", traineeId);
        }
        return isDeleted;
    }

    @Override
    public Optional<Trainee> findById(long traineeId) {
        logger.debug("Finding trainee by ID: {}", traineeId);
        Optional<Trainee> trainee = Optional.ofNullable(traineesStorage.get(traineeId));
        if (trainee.isPresent()) {
            logger.info("Found trainee: {}", trainee.get());
        } else {
            logger.warn("No trainee found with ID: {}", traineeId);
        }
        return trainee;
    }

    @Override
    public Optional<Trainee> findByUsername(String username) {

        if (username == null) {
            logger.warn("Username is null");
            return Optional.empty();
        }
        Optional<Trainee> trainee = traineesStorage.values()
                .stream()
                .filter(trainee1 -> {
                    User user = trainee1.getUser();
                    return user != null && username.equals(user.getUserName());
                })
                .findAny();
        if (trainee.isPresent()) {
            logger.info("Found trainee: {}", trainee.get());
        } else {
            logger.warn("No trainee found with username: {}", username);
        }
        return trainee;
    }

    @Override
    public List<Trainee> getAllTrainees() {
        return new ArrayList<>(traineesStorage.values());
    }

    @Override
    public List<Trainee> findAllByUsername(String username) {
        if (username == null) {
            logger.warn("Username pattern is null");
            return Collections.emptyList();
        }
        return traineesStorage.values()
                .stream()
                .filter(trainee -> {
                    User user = trainee.getUser();
                    return user != null && user.getUserName() != null && user.getUserName().matches(username + ".*");
                })
                .collect(Collectors.toList());
    }

    @Override
    public long getMaxId() {
        return traineesStorage.keySet().stream().max(Long::compareTo).orElse(0L) + 1;
    }
}

package com.epam.springcore.task.dao.impl;

import com.epam.springcore.task.dao.TraineeDAO;
import com.epam.springcore.task.model.Trainee;
import com.epam.springcore.task.model.User;
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

    private static final Logger logger = LoggerFactory.getLogger(TraineeDAOImpl.class);

    private final Map<Long, Trainee> traineesStorage;

    public TraineeDAOImpl(Map<Long, Trainee> traineesStorage) {
        this.traineesStorage = traineesStorage;
    }

    @Override
    public Optional<Trainee> create(Trainee trainee) {
       long traineeId = getMaxId();
        trainee.setTraineeId(traineeId);
        traineesStorage.put(traineeId, trainee);
        Optional<Trainee> createdTrainee = findById(traineeId);
        if (createdTrainee.isPresent()) {
            logger.debug("Successfully created trainee: {}", trainee);
        } else {
            logger.warn("Failed to create trainee with ID: {}", trainee.getTraineeId());
        }
        return createdTrainee;
    }

    @Override
    public Optional<Trainee> update(Trainee trainee) {
        Optional<Trainee> oldTrainee = Optional.ofNullable(traineesStorage.replace(trainee.getTraineeId(), trainee));
        if (oldTrainee.isPresent()) {
            logger.debug("Successfully updated trainee: {}", trainee);
        } else {
            logger.warn("Failed to update trainee with ID: {}", trainee.getTraineeId());
        }
        return oldTrainee;
    }

    @Override
    public boolean deleteById(long traineeId) {
        boolean isDeleted = traineesStorage.remove(traineeId) != null;
        if (isDeleted) {
            logger.debug("Successfully deleted trainee with ID: {}", traineeId);
        } else {
            logger.warn("Failed to delete trainee with ID: {}", traineeId);
        }
        return isDeleted;
    }

    @Override
    public Optional<Trainee> findById(long traineeId) {
        Optional<Trainee> trainee = Optional.ofNullable(traineesStorage.get(traineeId));
        if (trainee.isPresent()) {
            logger.debug("Found trainee: {}", trainee.get());
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
            logger.debug("Found trainee: {}", trainee.get());
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


    private long getMaxId() {
        return traineesStorage.keySet().stream().max(Long::compareTo).orElse(0L) + 1;
    }
}

package com.epam.springcore.task.dao.impl;


import com.epam.springcore.task.dao.TraineeDAO;
import com.epam.springcore.task.model.Trainee;
import com.epam.springcore.task.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class TraineeDAOImpl implements TraineeDAO {


    private final Map<Long, Trainee> traineesStorage;


    public TraineeDAOImpl(Map<Long, Trainee> traineesStorage) {
        this.traineesStorage = traineesStorage;
    }

    @Override
    public Optional<Trainee> create(long traineeId, Trainee trainee) {
        traineesStorage.put(traineeId, trainee);
        return findById(traineeId);
    }

    @Override
    public Optional<Trainee> update(Trainee trainee) {
        return Optional.ofNullable(traineesStorage.replace(trainee.getTraineeId(), trainee));
    }

    @Override
    public boolean deleteById(long traineeId) {
        return traineesStorage.remove(traineeId) != null;
    }

    @Override
    public Optional<Trainee> findById(long traineeId) {
        return Optional.ofNullable(traineesStorage.get(traineeId));
    }

    @Override
    public Optional<Trainee> findByUsername(String username) {

        if (username == null) {
            return Optional.empty();
        }
        return traineesStorage.values()
                .stream()
                .filter(trainee -> {
                    User user = trainee.getUser();
                    return user != null && username.equals(user.getUserName());
                })
                .findAny();
    }

    @Override
    public List<Trainee> getAllTrainees() {
        return new ArrayList<>(traineesStorage.values());
    }

    @Override
    public List<Trainee> findAllByUsername(String username) {
        if (username == null) {
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

package com.epam.springcore.task.dao.impl;

import com.epam.springcore.task.dao.TrainerDAO;
import com.epam.springcore.task.model.Trainer;
import com.epam.springcore.task.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class TrainerDAOImpl implements TrainerDAO {

    private final Map<Long, Trainer> trainersStorage;

    public TrainerDAOImpl(Map<Long, Trainer> trainersStorage) {
        this.trainersStorage = trainersStorage;
    }


    @Override
    public Optional<Trainer> create(long trainerId, Trainer trainer) {
        trainersStorage.put(trainerId, trainer);
        return getById(trainerId);
    }

    @Override
    public Optional<Trainer> update(Trainer trainer) {
        return Optional.ofNullable(trainersStorage.replace(trainer.getTrainerId(), trainer));
    }

    @Override
    public Optional<Trainer> getById(long trainerId) {
        return Optional.ofNullable(trainersStorage.get(trainerId));
    }

    @Override
    public Optional<Trainer> getByUsername(String username) {
        return trainersStorage.values()
                .stream()
                .filter(trainer -> {
                    User user = trainer.getUser();
                    return user != null && username.equals(user.getUserName());
                })
                .findAny();
    }

    @Override
    public List<Trainer> getAllTrainers() {
        return new ArrayList<>(trainersStorage.values());
    }

    @Override
    public List<Trainer> getAllTrainersByUsername(String username) {
        return trainersStorage.values()
                .stream()
                .filter(trainer -> {
                    User user = trainer.getUser();
                    return user != null && user.getUserName() != null && user.getUserName()
                            .matches(username + ".*");
                })
                .collect(Collectors.toList());
    }

    @Override
    public long getMaxId() {
        return trainersStorage.keySet().stream().max(Long::compareTo).orElse(0L) + 1;
    }
}

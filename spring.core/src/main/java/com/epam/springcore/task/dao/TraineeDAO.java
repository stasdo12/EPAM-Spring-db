package com.epam.springcore.task.dao;

import com.epam.springcore.task.model.Trainee;

import java.util.List;

import java.util.Optional;

public interface TraineeDAO {

    Optional<Trainee> create(Trainee trainee);

    Optional<Trainee> update(Trainee trainee);

    boolean deleteById(long traineeId);

    Optional<Trainee> findById(long traineeId);

    Optional<Trainee> findByUsername(String username);

    List<Trainee> getAllTrainees();

    List<Trainee> findAllByUsername(String username);


}

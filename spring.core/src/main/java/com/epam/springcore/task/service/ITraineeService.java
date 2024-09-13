package com.epam.springcore.task.service;

import com.epam.springcore.task.model.Trainee;
import com.epam.springcore.task.model.Trainer;
import com.epam.springcore.task.model.Training;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ITraineeService {

    Trainee saveTrainee(Trainee trainee);

    Optional<Trainee> findById(long traineeId);

    Optional<Trainee> findByUserId(Long userId);

    Optional<Trainee> findByUsername(String username);

    void changeTraineePassword(String username, String password);

    Trainee updateTraineeProfile(String username, Trainee updatedTrainee);

    void activateDeactivateTrainee(String username, boolean isActive);



    void deleteTrainer(String username);

    public boolean matchTrainerCredentials(String username, String password);

    List<Training> getTraineeTrainingsByCriteria(
            String traineeUsername, LocalDate fromDate, LocalDate toDate, String trainerUsername, String trainingName);



}

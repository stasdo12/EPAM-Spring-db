package com.epam.springcore.task.service;

import com.epam.springcore.task.model.Trainer;
import com.epam.springcore.task.model.Training;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ITrainerService {

    Trainer saveTrainer(Trainer trainer);

    Optional<Trainer> findById(Long trainerId);

    Optional<Trainer> findByUserId(Long userId);

    void changeTraineePassword(String username, String password);

    Trainer updateTrainerProfile(String username, Trainer updatedTrainer);

   void activateDeactivateTrainer(String username, boolean isActive);

    public boolean matchTrainerCredentials(String username, String password);

    Optional<Trainer> findByUsername(String username);

    List<Training> getTrainerTrainingsByCriteria(
            String trainerUsername, LocalDate fromDate, LocalDate toDate, String traineeUsername, String trainingName);

    List<Trainer> getTrainersNotAssignedToTrainee(String traineeUsername);


}

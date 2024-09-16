package com.epam.springcore.task.service;

import com.epam.springcore.task.dto.TrainerDTO;
import com.epam.springcore.task.dto.TrainingDTO;
import com.epam.springcore.task.model.Trainer;
import com.epam.springcore.task.model.Training;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ITrainerService {

    TrainerDTO saveTrainer(TrainerDTO trainer);

    Optional<TrainerDTO> findById(Long trainerId);

    void changeTrainerPassword(String username, String password);

    TrainerDTO updateTrainerProfile(String username, TrainerDTO updatedTrainer);

   void activateDeactivateTrainer(String username, boolean isActive);

    public boolean matchTrainerCredentials(String username, String password);

    Optional<TrainerDTO> findByUsername(String username);

    List<TrainingDTO> getTrainerTrainingsByCriteria(
            String trainerUsername, LocalDate fromDate, LocalDate toDate, String traineeUsername, String trainingName);

    List<TrainerDTO> getTrainersNotAssignedToTrainee(String traineeUsername);


}

package com.epam.springcore.task.service;

import com.epam.springcore.task.dto.PassUsernameDTO;
import com.epam.springcore.task.dto.TrainerDTO;
import com.epam.springcore.task.dto.TrainingDTO;
import com.epam.springcore.task.model.Trainer;
import com.epam.springcore.task.model.Training;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ITrainerService {

    PassUsernameDTO saveTrainer(TrainerDTO trainer);

    Optional<TrainerDTO> findById(Long trainerId);

    PassUsernameDTO changeTrainerPassword(PassUsernameDTO passUsernameDTO);

    TrainerDTO updateTrainerProfile(String username, TrainerDTO updatedTrainer);

   void activateDeactivateTrainer(String username, boolean isActive);

    public boolean matchTrainerCredentials(PassUsernameDTO passUsernameDTO);

    Optional<TrainerDTO> findByUsername(String username);

    List<TrainingDTO> getTrainerTrainingsByCriteria(
            String trainerUsername, LocalDate fromDate, LocalDate toDate, String traineeUsername, String trainingName);

    List<TrainerDTO> getTrainersNotAssignedToTrainee(String traineeUsername);


}

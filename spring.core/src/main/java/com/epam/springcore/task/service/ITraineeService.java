package com.epam.springcore.task.service;

import com.epam.springcore.task.dto.PassUsernameDTO;
import com.epam.springcore.task.dto.TraineeDTO;
import com.epam.springcore.task.dto.TrainerDTO;
import com.epam.springcore.task.dto.TrainingDTO;
import com.epam.springcore.task.model.Trainee;



import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ITraineeService {

    PassUsernameDTO saveTrainee(TraineeDTO traineeDTO);

    Optional<TraineeDTO> findById(long traineeId);

    Optional<TraineeDTO> findByUserId(Long userId);

    Optional<TraineeDTO> findByUsername(String username);

    PassUsernameDTO changeTraineePassword(PassUsernameDTO passUsernameDTO);

    TraineeDTO updateTraineeProfile(String username, TraineeDTO updatedTrainee);

    void activateDeactivateTrainee(String username, boolean isActive);

    void deleteTrainee(String username);

    boolean matchTraineeCredentials(PassUsernameDTO passUsernameDTO);

    List<TrainingDTO> getTraineeTrainingsByCriteria(
            String traineeUsername, LocalDate fromDate, LocalDate toDate, String trainerUsername, String trainingName);

    Trainee updateTraineeTrainers(String traineeUsername, Set<TrainerDTO> newTrainerDTOs);
}

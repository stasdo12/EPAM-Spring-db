package com.epam.springcore.task.facade;

import com.epam.springcore.task.dto.PassUsernameDTO;
import com.epam.springcore.task.dto.TraineeDTO;
import com.epam.springcore.task.dto.TrainerDTO;
import com.epam.springcore.task.dto.TrainingDTO;
import com.epam.springcore.task.model.Trainee;
import com.epam.springcore.task.service.impl.TraineeService;
import com.epam.springcore.task.service.impl.TrainerService;
import com.epam.springcore.task.service.impl.TrainingService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
@Slf4j
public class GymFacade {

    private final TraineeService traineeService;

    private final TrainerService trainerService;

    private final TrainingService trainingService;


    @Autowired
    public GymFacade(TraineeService traineeService, TrainerService trainerService, TrainingService trainingService) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.trainingService = trainingService;
    }



    public boolean matchTrainerCredentialsTrainee(PassUsernameDTO passUsernameDTO) {
        return traineeService.matchTraineeCredentials(passUsernameDTO);
    }

    public Optional<TraineeDTO> findTraineeByUsername(@Valid String username) {
        return traineeService.findByUsername(username);
    }

    public PassUsernameDTO changeTraineePassword(PassUsernameDTO passUsernameDTO) {
        traineeService.changeTraineePassword(passUsernameDTO);
        return passUsernameDTO;
    }

    public Optional<TraineeDTO> updateTrainee(@Valid String username, TraineeDTO traineeDTO) {
        return Optional.ofNullable(traineeService.updateTraineeProfile(username, traineeDTO));
    }

    public void activateDeactivateTrainee(String username, boolean isActive) {
        traineeService.activateDeactivateTrainee(username, isActive);
    }

    public void deleteTrainee(String username) {
        traineeService.deleteTrainee(username);
    }

    public List<TrainingDTO> getTraineeTrainingsByCriteria(String traineeUsername, LocalDate fromDate, LocalDate toDate,
                                                           String trainerUsername, String trainingName) {
        return traineeService.getTraineeTrainingsByCriteria(traineeUsername, fromDate, toDate,
                trainerUsername, trainingName);
    }

    public Trainee updateTraineeTrainers(@Valid String traineeUsername, Set<TrainerDTO> newTrainers) {
        return traineeService.updateTraineeTrainers(traineeUsername, newTrainers);
    }


    public TrainerDTO updateTrainerProfile(@Valid String username, TrainerDTO updatedTrainerDTO){
        return trainerService.updateTrainerProfile(username, updatedTrainerDTO);
    }

    public void activateDeactivateTrainer(String username, boolean isActive){
        trainerService.activateDeactivateTrainer(username, isActive);
    }

    public List<TrainingDTO> getTrainerTrainingsByCriteria(String trainerUsername, LocalDate fromDate,
                                                        LocalDate toDate, String traineeUsername, String trainingName){
        return trainerService.getTrainerTrainingsByCriteria(trainerUsername, fromDate, toDate,
                traineeUsername, trainingName);
    }

    public List<TrainerDTO> getTrainersNotAssignedToTrainee(@Valid String traineeUsername){
        return trainerService.getTrainersNotAssignedToTrainee(traineeUsername);
    }

    public TrainingDTO addTraining(@Valid TrainingDTO trainingDTO){
       return trainingService.addTraining(trainingDTO);
    }
}

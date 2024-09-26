package com.epam.springcore.task.facade;

import com.epam.springcore.task.dto.PassUsernameDTO;
import com.epam.springcore.task.dto.TraineeDTO;
import com.epam.springcore.task.dto.TrainerDTO;
import com.epam.springcore.task.dto.TrainingDTO;
import com.epam.springcore.task.model.Trainee;
import com.epam.springcore.task.service.impl.TraineeService;
import com.epam.springcore.task.service.impl.TrainerService;
import com.epam.springcore.task.service.impl.TrainingService;
import com.epam.springcore.task.service.impl.UserService;
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

    public PassUsernameDTO saveTrainer(TrainerDTO trainerDTO){
        return trainerService.saveTrainer(trainerDTO);
    }

    public Optional<TrainerDTO> findTrainerById(Long trainerId){
        return trainerService.findById(trainerId);
    }

    public PassUsernameDTO changeTrainerPassword(PassUsernameDTO passUsernameDTO){
        return trainerService.changeTrainerPassword(passUsernameDTO);
    }

    public TrainerDTO updateTrainerProfile(@Valid String username, TrainerDTO updatedTrainerDTO){
        return trainerService.updateTrainerProfile(username, updatedTrainerDTO);
    }

    public void activateDeactivateTrainer(String username, boolean isActive){
        trainerService.activateDeactivateTrainer(username, isActive);
    }

    public boolean matchTrainerCredentialsTrainee(PassUsernameDTO passUsernameDTO) {
        return trainerService.matchTrainerCredentials(passUsernameDTO);
    }

    public Optional<TrainerDTO> findTrainerByUsername(String username){
        return trainerService.findByUsername(username);
    }

    public List<TrainingDTO> getTrainerTrainingsByCriteria(String trainerUsername, LocalDate fromDate,
                                                           LocalDate toDate, String traineeUsername, String trainingName){
        return trainerService.getTrainerTrainingsByCriteria(trainerUsername, fromDate, toDate,
                traineeUsername, trainingName);
    }

    public List<TrainerDTO> getTrainersNotAssignedToTrainee(@Valid String traineeUsername){
        return trainerService.getTrainersNotAssignedToTrainee(traineeUsername);
    }

    public PassUsernameDTO saveTrainee(TraineeDTO traineeDTO){
        return traineeService.saveTrainee(traineeDTO);
    }

    public Optional<TraineeDTO> findTraineeById(long traineeId){
        return traineeService.findById(traineeId);
    }

    public Optional<TraineeDTO> findTraineeByUserId(Long userId){
        return traineeService.findByUserId(userId);
    }

    public Optional<TraineeDTO> findTraineeByUsername(String username){
        return traineeService.findByUsername(username);
    }

    public PassUsernameDTO changeTraineePassword(PassUsernameDTO passUsernameDTO){
        return traineeService.changeTraineePassword(passUsernameDTO);
    }

    public TraineeDTO updateTraineeProfile(String username, TraineeDTO traineeDTO){
        return traineeService.updateTraineeProfile(username, traineeDTO);
    }

    public void activateDeactivateTraineeProfile(String username, boolean isActive){
        traineeService.activateDeactivateTrainee(username, isActive);
    }

    public void deleteTrainee(String username){
        traineeService.deleteTrainee(username);
    }


    public boolean matchTraineeCredentials(PassUsernameDTO passUsernameDTO){
        return traineeService.matchTraineeCredentials(passUsernameDTO);
    }

    public List<TrainingDTO> getTraineeTrainingsByCriteria(String traineeUsername,
                                                           LocalDate fromDate,
                                                           LocalDate toDate,
                                                           String trainerUsername,
                                                           String trainingName){
        return traineeService.getTraineeTrainingsByCriteria(traineeUsername,
                fromDate, toDate,
                trainerUsername, trainingName);
    }

    public Trainee updateTraineeTrainers(String traineeUsername, Set<TrainerDTO> newTrainerDTOs){
        return traineeService.updateTraineeTrainers(traineeUsername, newTrainerDTOs);
    }

    public TrainingDTO addTraining (TrainingDTO trainingDTO){
        return trainingService.addTraining(trainingDTO);
    }

}

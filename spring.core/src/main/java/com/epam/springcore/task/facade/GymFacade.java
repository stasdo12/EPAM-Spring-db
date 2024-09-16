package com.epam.springcore.task.facade;

import com.epam.springcore.task.dto.TraineeDTO;
import com.epam.springcore.task.dto.TrainerDTO;
import com.epam.springcore.task.dto.TrainingDTO;
import com.epam.springcore.task.model.Trainee;
import com.epam.springcore.task.model.Trainer;
import com.epam.springcore.task.model.Training;
import com.epam.springcore.task.service.impl.TraineeService;
import com.epam.springcore.task.service.impl.TrainerService;
import com.epam.springcore.task.service.impl.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
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

    public TraineeDTO saveTrainee(TraineeDTO traineeDTO) {
        return traineeService.saveTrainee(traineeDTO);
    }

    public boolean matchTrainerCredentialsTrainee(String username, String password) {
        return traineeService.matchTrainerCredentials(username, password);
    }

    public Optional<TraineeDTO> findTraineeByUsername(String username) {
        return traineeService.findByUsername(username);
    }

    public void changeTraineePassword(String username, String newPassword) {
        traineeService.changeTraineePassword(username, newPassword);
    }

    public Optional<TraineeDTO> updateTrainee(String username, TraineeDTO traineeDTO) {
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

    public Trainee updateTraineeTrainers(String traineeUsername, Set<TrainerDTO> newTrainers) {
        return traineeService.updateTraineeTrainers(traineeUsername, newTrainers);
    }
}

//    public Trainer saveTrainer(Trainer trainer){
//       return trainerService.saveTrainer(trainer);
//    }
//
//    public boolean matchTrainerCredentialsTrainer(String username, String password){
//        return trainerService.matchTrainerCredentials(username, password);
//    }
//
//    public Optional<Trainer> findTrainerByUsername(String username){
//        return trainerService.findByUsername(username);
//    }
//
//    public void changeTrainerPassword(String username, String newPassword){
//        trainerService.changeTrainerPassword(username, newPassword);
//    }
//
//    public Trainer updateTrainerProfile(String username, Trainer updatedTrainer){
//        return trainerService.updateTrainerProfile(username, updatedTrainer);
//    }
//
//    public void activateDeactivateTrainer(String username, boolean isActive){
//        trainerService.activateDeactivateTrainer(username, isActive);
//    }
//
//    public List<Training> getTrainerTrainingsByCriteria(String trainerUsername, LocalDate fromDate,
//                                                        LocalDate toDate, String traineeUsername, String trainingName){
//        return trainerService.getTrainerTrainingsByCriteria(trainerUsername, fromDate, toDate,
//                traineeUsername, trainingName);
//    }
//
//    public List<Trainer> getTrainersNotAssignedToTrainee(String traineeUsername){
//        return trainerService.getTrainersNotAssignedToTrainee(traineeUsername);
//    }
//
//    public Training addTraining(Training training){
//       return trainingService.addTraining(training);
//    }
//}

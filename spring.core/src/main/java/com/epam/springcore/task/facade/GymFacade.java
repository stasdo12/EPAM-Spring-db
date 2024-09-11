package com.epam.springcore.task.facade;


import com.epam.springcore.task.model.Trainee;
import com.epam.springcore.task.model.Trainer;
import com.epam.springcore.task.model.Training;
import com.epam.springcore.task.service.TraineeService;
import com.epam.springcore.task.service.TrainerService;
import com.epam.springcore.task.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GymFacade{

    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingService trainingService;

    @Autowired
    public GymFacade(TraineeService traineeService, TrainerService trainerService, TrainingService trainingService) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.trainingService = trainingService;
    }

    public Optional<Trainee> createTrainee(Trainee trainee) {
        return traineeService.create(trainee);
    }

    public Optional<Trainee> updateTrainee(Trainee trainee) {
        return traineeService.update(trainee);
    }

    public List<Trainee> getAllTrainees() {
        return traineeService.getTrainees();
    }

    public Optional<Trainee> getTraineeById(long traineeId) {
        return traineeService.getById(traineeId);
    }

    public Optional<Trainer> createTrainer(Trainer trainer) {
        return trainerService.create(trainer);
    }

    public List<Trainer> getAllTrainers() {
        return trainerService.getAllTrainers();
    }

    public Optional<Trainer> getTrainerById(long trainerId) {
        return trainerService.getById(trainerId);
    }

    public Optional<Training> createTraining(Training training) {
        return trainingService.create(training);
    }

    public List<Training> getAllTrainings() {
        return trainingService.getAllTrainings();
    }

    public Optional<Training> getTrainingById(long trainingId) {
        return trainingService.findById(trainingId);
    }

}

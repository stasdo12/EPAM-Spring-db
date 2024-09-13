package com.epam.springcore.task.service.impl;

import com.epam.springcore.task.dao.TrainingRepository;
import com.epam.springcore.task.model.Training;
import com.epam.springcore.task.service.ITrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainingService implements ITrainingService {

    public final TrainingRepository trainingRepository;


    @Autowired
    public TrainingService(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }


    @Override
    public Training addTraining(Training training) {
        if(training == null || training.getTrainee() == null || training.getTrainer() == null){
            throw new IllegalArgumentException("Training and associated Trainee/Trainer must not be null");
        }
        return trainingRepository.save(training);
    }
}

package com.epam.springcore.task.service.impl;

import com.epam.springcore.task.repo.TrainingRepository;
import com.epam.springcore.task.dto.TrainingDTO;
import com.epam.springcore.task.mapper.TrainingMapper;
import com.epam.springcore.task.model.Training;
import com.epam.springcore.task.service.ITrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainingService implements ITrainingService {

    public final TrainingRepository trainingRepository;
    public final TrainingMapper trainingMapper;

    @Autowired
    public TrainingService(TrainingRepository trainingRepository, TrainingMapper trainingMapper) {
        this.trainingRepository = trainingRepository;
        this.trainingMapper = trainingMapper;
    }

    @Override
    public TrainingDTO addTraining(TrainingDTO trainingDTO) {
        if(trainingDTO == null || trainingDTO.getTrainee() == null || trainingDTO.getTrainer() == null){
            throw new IllegalArgumentException("Training and associated Trainee/Trainer must not be null");
        }

        Training training = trainingMapper.trainingToEntity(trainingDTO);
        Training savedTraining = trainingRepository.save(training);
        return trainingMapper.trainingToDTO(savedTraining);
    }
}

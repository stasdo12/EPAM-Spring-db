package com.epam.springcore.task.service.impl;

import com.epam.springcore.task.dao.TrainingRepository;
import com.epam.springcore.task.dto.TrainingDTO;
import com.epam.springcore.task.mapper.TrainingMapper;
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
    public TrainingDTO addTraining(TrainingDTO trainingDTO) {
        if(trainingDTO == null || trainingDTO.getTrainee() == null || trainingDTO.getTrainer() == null){
            throw new IllegalArgumentException("Training and associated Trainee/Trainer must not be null");
        }

        Training training = TrainingMapper.INSTANCE.trainingToEntity(trainingDTO);
        Training savedTraining = trainingRepository.save(training);
        return TrainingMapper.INSTANCE.trainingToDTO(savedTraining);
    }
}

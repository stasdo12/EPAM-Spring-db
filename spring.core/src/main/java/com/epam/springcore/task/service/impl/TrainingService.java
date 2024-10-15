package com.epam.springcore.task.service.impl;

import com.epam.springcore.task.model.Trainee;
import com.epam.springcore.task.model.Trainer;
import com.epam.springcore.task.model.TrainingType;
import com.epam.springcore.task.repo.TraineeRepository;
import com.epam.springcore.task.repo.TrainerRepository;
import com.epam.springcore.task.repo.TrainingRepository;
import com.epam.springcore.task.dto.TrainingDTO;
import com.epam.springcore.task.mapper.TrainingMapper;
import com.epam.springcore.task.model.Training;
import com.epam.springcore.task.repo.TrainingTypeRepository;
import com.epam.springcore.task.service.ITrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainingService implements ITrainingService {

    public final TrainingRepository trainingRepository;
    public final TrainingMapper trainingMapper;

    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;
    private final TrainingTypeRepository trainingTypeRepository;




    @Autowired
    public TrainingService(TrainingRepository trainingRepository, TrainingMapper trainingMapper, TraineeRepository traineeRepository, TrainerRepository trainerRepository, TrainingTypeRepository trainingTypeRepository) {
        this.trainingRepository = trainingRepository;
        this.trainingMapper = trainingMapper;
        this.traineeRepository = traineeRepository;
        this.trainerRepository = trainerRepository;
        this.trainingTypeRepository = trainingTypeRepository;
    }

    @Override
    public TrainingDTO addTraining(TrainingDTO trainingDTO) {
        if(trainingDTO == null || trainingDTO.getTrainee() == null || trainingDTO.getTrainer() == null){
            throw new IllegalArgumentException("Training and associated Trainee/Trainer must not be null");
        }

        TrainingType trainingType = trainingTypeRepository.findByName(trainingDTO.getTrainingType().getName());
        Trainee trainee = traineeRepository.findTraineeByUserUsername(trainingDTO.getTrainee().getUser().getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Trainee not found"));

        Trainer trainer = trainerRepository.findTrainerByUserUsername(trainingDTO.getTrainer().getUser().getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Trainer not found"));

        Training training = trainingMapper.trainingToEntity(trainingDTO);
        training.setTrainee(trainee);
        training.setTrainer(trainer);
        training.setTrainingType(trainingType);
        Training savedTraining = trainingRepository.save(training);
        return trainingMapper.trainingToDTO(savedTraining);
    }
}

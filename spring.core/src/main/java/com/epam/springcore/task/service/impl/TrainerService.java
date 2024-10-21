package com.epam.springcore.task.service.impl;

import com.epam.springcore.task.model.*;
import com.epam.springcore.task.repo.TraineeRepository;
import com.epam.springcore.task.repo.TrainerRepository;
import com.epam.springcore.task.repo.TrainingRepository;
import com.epam.springcore.task.dto.PassUsernameDTO;
import com.epam.springcore.task.dto.TrainerDTO;
import com.epam.springcore.task.dto.TrainingDTO;
import com.epam.springcore.task.mapper.TrainerMapper;
import com.epam.springcore.task.mapper.TrainingMapper;
import com.epam.springcore.task.mapper.TrainingTypeMapper;
import com.epam.springcore.task.repo.TrainingTypeRepository;
import com.epam.springcore.task.service.ITrainerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TrainerService  implements ITrainerService {
    private final TrainerRepository trainerRepository;
    private final TraineeRepository traineeRepository;
    private final TrainingRepository trainingRepository;
    private final TrainingTypeRepository trainingTypeRepository;
    private final TrainingMapper trainingMapper;
    private final TrainerMapper trainerMapper;
    private final TrainingTypeMapper trainingTypeMapper;
    private final UserService userService;

    @Autowired
    public TrainerService(TrainerRepository trainerRepository,
                          TraineeRepository traineeRepository,
                          TrainingRepository trainingRepository,
                          TrainingTypeRepository trainingTypeRepository, TrainingMapper trainingMapper,
                          TrainerMapper trainerMapper,
                          TrainingTypeMapper trainingTypeMapper,
                          UserService userService
                          ) {
        this.trainerRepository = trainerRepository;
        this.traineeRepository = traineeRepository;
        this.trainingRepository = trainingRepository;
        this.trainingTypeRepository = trainingTypeRepository;
        this.trainingMapper = trainingMapper;

        this.trainerMapper = trainerMapper;
        this.trainingTypeMapper = trainingTypeMapper;
        this.userService = userService;
    }

    @Override
    @Transactional
    public PassUsernameDTO saveTrainer(TrainerDTO trainerDTO) {

        if (trainerDTO == null || trainerDTO.getUser() == null) {
            throw new IllegalArgumentException("Trainer and associated User must not be null");
        }

        Trainer trainer = trainerMapper.trainerToEntity(trainerDTO);

        TrainingType specialization = trainingTypeRepository.findByName(trainerDTO.getSpecialization().getName());
        if (specialization == null) {
            throw new IllegalArgumentException("Specialization not found");
        }
        trainer.setSpecialization(specialization);

        User user = trainer.getUser();
        PassUsernameDTO passUsernameDTO = userService.generateAndSaveUser(user);
        trainer.setUser(user);

        trainerRepository.save(trainer);
        return passUsernameDTO;
    }

    @Override
    public boolean matchTrainerCredentials(PassUsernameDTO passUsernameDTO) {
        return userService.matchUserCredentials(passUsernameDTO);
    }

    @Override
    public Optional<TrainerDTO> findByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username must not be null or empty");
        }
        Optional<Trainer> trainerOptional = trainerRepository.findTrainerByUserUsername(username);
        if (trainerOptional.isEmpty()) {
            return Optional.empty();
        }
        TrainerDTO trainerDTO = trainerMapper.trainerToDTO(trainerOptional.get());
        return Optional.of(trainerDTO);
    }

    @Override
    @Transactional
    public PassUsernameDTO changeTrainerPassword(PassUsernameDTO passUsernameDTO) {
        Optional<Trainer> trainerOptional = trainerRepository.findTrainerByUserUsername(passUsernameDTO.getUsername());
        if (trainerOptional.isEmpty()){
            throw new IllegalArgumentException("Trainer with username " + passUsernameDTO.getUsername() +  " not found");
        }
        Trainer trainer = trainerOptional.get();
        User user = trainer.getUser();
        if (user == null){
            throw new IllegalArgumentException("User associated with trainer is null");
        }
        userService.changeUserPassword(user.getUsername(), passUsernameDTO.getPassword());
        return passUsernameDTO;
    }

    @Override
    public TrainerDTO updateTrainerProfile(String username, TrainerDTO updatedTrainerDTO) {
        Optional<Trainer> trainerOptional = trainerRepository.findTrainerByUserUsername(username);
        if (trainerOptional.isEmpty()) {
            throw new IllegalArgumentException("Trainer with username " + username + " not found");
        }
        Trainer trainer = trainerOptional.get();

        trainer.setSpecialization(trainingTypeMapper.trainingTypeToEntity(updatedTrainerDTO.getSpecialization()));
        trainer.setTrainings(trainingMapper.dtoListToEntityList(updatedTrainerDTO.getTrainings()));

        Trainer updatedTrainer = trainerRepository.save(trainer);
        return trainerMapper.trainerToDTO(updatedTrainer);
    }

    @Override
    public void activateDeactivateTrainer(String username, boolean isActive) {
        Optional<Trainer> trainerOptional = trainerRepository.findTrainerByUserUsername(username);
        if (trainerOptional.isEmpty()){
            throw new IllegalArgumentException("Trainer with username " + username + " not found");
        }
        Trainer trainer = trainerOptional.get();
        User user = trainer.getUser();

        if (user == null){
            throw new IllegalArgumentException("User associated with trainer is null");
        }
        userService.activateDeactivateUser(user.getUsername(), isActive);
    }

    @Override
    public List<TrainingDTO> getTrainerTrainingsByCriteria(String trainerUsername,
                                                           LocalDate fromDate,
                                                           LocalDate toDate,
                                                           String traineeUsername,
                                                           String trainingName) {
        List<Training> trainings =
                trainingRepository.findByTrainerUserUsernameAndDateBetweenAndTraineeUserUsernameAndTrainingName(
                trainerUsername, fromDate, toDate, traineeUsername, trainingName);
        return trainingMapper.entityListToDTOList(trainings);
    }

    @Override
    public List<TrainerDTO> getTrainersNotAssignedToTrainee(String traineeUsername) {
        if (traineeUsername == null){
            throw new IllegalArgumentException("Trainee username must not be null");
        }
        Optional<Trainee> trainee = traineeRepository.findTraineeByUserUsername(traineeUsername);
        if (trainee.isEmpty()){
            throw new IllegalArgumentException("Trainee with username " + traineeUsername + "not found");
        }
        List<Trainer> trainers = trainerRepository.findTrainersNotAssignedToTrainee(traineeUsername);
        return trainers.stream()
                .map(trainerMapper::trainerToDTO)
                .toList();
    }

    @Override
    public Optional<TrainerDTO> findById(Long trainerId) {
        Optional<Trainer> trainerOptional = trainerRepository.findById(trainerId);
        return trainerOptional.map(trainerMapper::trainerToDTO);
    }

}

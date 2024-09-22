package com.epam.springcore.task.service.impl;

import com.epam.springcore.task.repo.TraineeRepository;
import com.epam.springcore.task.repo.TrainerRepository;
import com.epam.springcore.task.repo.TrainingRepository;
import com.epam.springcore.task.repo.UserRepository;
import com.epam.springcore.task.dto.PassUsernameDTO;
import com.epam.springcore.task.dto.TrainerDTO;
import com.epam.springcore.task.dto.TrainingDTO;
import com.epam.springcore.task.mapper.TrainerMapper;
import com.epam.springcore.task.mapper.TrainingMapper;
import com.epam.springcore.task.mapper.TrainingTypeMapper;
import com.epam.springcore.task.model.Trainee;
import com.epam.springcore.task.model.Trainer;
import com.epam.springcore.task.model.Training;
import com.epam.springcore.task.model.User;
import com.epam.springcore.task.service.ITrainerService;
import com.epam.springcore.task.utils.NameGenerator;
import com.epam.springcore.task.utils.PasswordGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TrainerService  implements ITrainerService {

    private final NameGenerator nameGeneration;

    private final PasswordGenerator passwordGenerator;

    private final UserRepository userRepository;

    private final TrainerRepository trainerRepository;


    private final TraineeRepository traineeRepository;

    private final TrainingRepository trainingRepository;

    private final PasswordEncoder passwordEncoder;






    @Autowired
    public TrainerService(NameGenerator nameGeneration,
                          PasswordGenerator passwordGenerator,
                          UserRepository userRepository,
                          TrainerRepository trainerRepository,
                          TraineeRepository traineeRepository,
                          TrainingRepository trainingRepository, PasswordEncoder passwordEncoder) {
        this.nameGeneration = nameGeneration;
        this.passwordGenerator = passwordGenerator;
        this.userRepository = userRepository;
        this.trainerRepository = trainerRepository;
        this.traineeRepository = traineeRepository;
        this.trainingRepository = trainingRepository;
        this.passwordEncoder = passwordEncoder;

    }

    @Override
    @Transactional
    public PassUsernameDTO saveTrainer(TrainerDTO trainerDTO) {

        if (trainerDTO == null || trainerDTO.getUser() == null) {
            throw new IllegalArgumentException("Trainer and associated User must not be null");
        }

        Trainer trainer  = TrainerMapper.INSTANCE.trainerToEntity(trainerDTO);

        User user = trainer.getUser();

        String generatedUsername = nameGeneration.generateUniqueUsername(user);

        String generatedPassword = passwordGenerator.generatePassword();
        user.setUsername(generatedUsername);
        String hashedPassword = passwordEncoder.encode(generatedPassword);
        user.setPassword(hashedPassword);
        user.setActive(true);
        trainer.setUser(user);
        trainerRepository.save(trainer);

        return new PassUsernameDTO(generatedUsername, generatedPassword);
    }

    @Override
    public boolean matchTrainerCredentials(PassUsernameDTO passUsernameDTO) {
        Optional<User> optionalUser = userRepository.findByUsername(passUsernameDTO.getUsername());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return passwordEncoder.matches(passUsernameDTO.getPassword(), user.getPassword());
        }
        return false;
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
        TrainerDTO trainerDTO = TrainerMapper.INSTANCE.trainerToDTO(trainerOptional.get());
        return Optional.of(trainerDTO);
    }

    @Override
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

        String hashedPassword = passwordEncoder.encode(passUsernameDTO.getPassword());
        user.setPassword(hashedPassword);
        trainerRepository.save(trainer);
        return passUsernameDTO;
    }

    @Override
    public TrainerDTO updateTrainerProfile(String username, TrainerDTO updatedTrainerDTO) {
        Optional<Trainer> trainerOptional = trainerRepository.findTrainerByUserUsername(username);
        if (trainerOptional.isEmpty()) {
            throw new IllegalArgumentException("Trainer with username " + username + " not found");
        }
        Trainer trainer = trainerOptional.get();

        trainer.setSpecialization(TrainingTypeMapper.INSTANCE.trainingTypeToEntity(updatedTrainerDTO.getSpecialization()));
        trainer.setTrainings(TrainingMapper.INSTANCE.dtoListToEntityList(updatedTrainerDTO.getTrainings()));

        Trainer updatedTrainer = trainerRepository.save(trainer);
        return TrainerMapper.INSTANCE.trainerToDTO(updatedTrainer);
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
        user.setActive(isActive);
        trainerRepository.save(trainer);
    }

    @Override
    public List<TrainingDTO> getTrainerTrainingsByCriteria(String trainerUsername,
                                                           LocalDate fromDate,
                                                           LocalDate toDate,
                                                           String traineeUsername,
                                                           String trainingName) {
        List<Training> trainings =
                trainingRepository.findByTrainer_User_UsernameAndDateBetweenAndTrainee_User_UsernameAndTrainingName(
                trainerUsername, fromDate, toDate, traineeUsername, trainingName);
        return TrainingMapper.INSTANCE.entityListToDTOList(trainings);
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
        return TrainerMapper.INSTANCE.entityListToDTOList(trainers);
    }

    @Override
    public Optional<TrainerDTO> findById(Long trainerId) {
        Optional<Trainer> trainerOptional = trainerRepository.findById(trainerId);
        return trainerOptional.map(TrainerMapper.INSTANCE::trainerToDTO);
    }
}

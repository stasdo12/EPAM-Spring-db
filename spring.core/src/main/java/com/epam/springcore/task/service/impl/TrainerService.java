package com.epam.springcore.task.service.impl;


import com.epam.springcore.task.dao.TraineeRepository;
import com.epam.springcore.task.dao.TrainerRepository;
import com.epam.springcore.task.dao.TrainingRepository;
import com.epam.springcore.task.dao.UserRepository;
import com.epam.springcore.task.model.Trainee;
import com.epam.springcore.task.model.Trainer;
import com.epam.springcore.task.model.Training;
import com.epam.springcore.task.model.User;
import com.epam.springcore.task.service.ITrainerService;
import com.epam.springcore.task.utils.NameGenerator;
import com.epam.springcore.task.utils.PasswordGenerator;
import com.epam.springcore.task.utils.impl.AuthenticationUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static final Logger log = LoggerFactory.getLogger(TrainerService.class);





    @Autowired
    public TrainerService(NameGenerator nameGeneration, PasswordGenerator passwordGenerator, UserRepository userRepository, TrainerRepository trainerRepository, TraineeRepository traineeRepository, TrainingRepository trainingRepository) {
        this.nameGeneration = nameGeneration;
        this.passwordGenerator = passwordGenerator;
        this.userRepository = userRepository;
        this.trainerRepository = trainerRepository;
        this.traineeRepository = traineeRepository;
        this.trainingRepository = trainingRepository;
    }


    @Override
    @Transactional
    public Trainer saveTrainer(Trainer trainer) {

        if (trainer == null || trainer.getUser() == null) {
            throw new IllegalArgumentException("Trainer and associated User must not be null");
        }

        User user = trainer.getUser();

        String generatedUsername = nameGeneration.generateUniqueUsername(user, userRepository,
                traineeRepository.findAll(), trainerRepository.findAll());

        user.setUsername(generatedUsername);
        user.setPassword(passwordGenerator.generatePassword());
        user.setActive(true);

        trainer.setUser(user);
        return trainerRepository.save(trainer);
        //TODO DTO
    }

    @Override
    public boolean matchTrainerCredentials(String username, String password) {
        return AuthenticationUtils.matchCredentials(username, password, userRepository);
    }

    @Override
    public Optional<Trainer> findByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username must not be null or empty");
        }
        Optional<Trainer> trainerOptional = trainerRepository.findTrainerByUserUsername(username);
        if (trainerOptional.isEmpty()) {
            log.debug("Trainer not found for username: {}", username);
        }
        return trainerOptional;
    }


    @Override
    public void changeTraineePassword(String username, String newPassword) {
        Optional<Trainer> trainerOptional = trainerRepository.findTrainerByUserUsername(username);
        if (trainerOptional.isEmpty()){
            throw new IllegalArgumentException("Trainer with username" + username +  "not found");
        }
        Trainer trainer = trainerOptional.get();
        User user = trainer.getUser();
        if (user == null){
            throw new IllegalArgumentException("User associated with trainer is null");
        }
        user.setPassword(newPassword);
        trainerRepository.save(trainer);

    }

    @Override
    public Trainer updateTrainerProfile(String username, Trainer updatedTrainer) {
        Optional<Trainer> trainerOptional = trainerRepository.findTrainerByUserUsername(username);

        if (trainerOptional.isEmpty()) {
            throw new IllegalArgumentException("Trainer with username " + username + " not found");
        }
        Trainer trainer = trainerOptional.get();

            trainer.setSpecialization(updatedTrainer.getSpecialization());
            trainer.setTrainings(updatedTrainer.getTrainings());

        return trainerRepository.save(trainer);

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
    public List<Training> getTrainerTrainingsByCriteria(String trainerUsername, LocalDate fromDate,
                                                        LocalDate toDate, String traineeUsername, String trainingName) {
        return trainingRepository.findByTrainer_User_UsernameAndDateBetweenAndTrainee_User_UsernameAndTrainingName
                (trainerUsername, fromDate, toDate, traineeUsername, trainingName);
    }

    @Override
    public List<Trainer> getTrainersNotAssignedToTrainee(String traineeUsername) {
        if (traineeUsername == null){
            throw new IllegalArgumentException("Trainee username must not be null");
        }
        Optional<Trainee> trainee = traineeRepository.findTraineeByUserUsername(traineeUsername);
        if (trainee.isEmpty()){
            throw new IllegalArgumentException("Trainee with username " + traineeUsername + "not found");
        }
        return trainerRepository.findTrainersNotAssignedToTrainee(traineeUsername);
    }


    @Override
    public Optional<Trainer> findById(Long trainerId) {
        return Optional.empty();
    }

    @Override
    public Optional<Trainer> findByUserId(Long userId) {
        return Optional.empty();
    }






}

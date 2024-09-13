package com.epam.springcore.task.service.impl;

import com.epam.springcore.task.dao.TraineeRepository;
import com.epam.springcore.task.dao.TrainerRepository;
import com.epam.springcore.task.dao.UserRepository;
import com.epam.springcore.task.model.Trainee;
import com.epam.springcore.task.model.Trainer;
import com.epam.springcore.task.model.User;
import com.epam.springcore.task.service.ITraineeService;
import com.epam.springcore.task.utils.NameGenerator;
import com.epam.springcore.task.utils.PasswordGenerator;
import com.epam.springcore.task.utils.impl.AuthenticationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TraineeService implements ITraineeService {

    private final NameGenerator nameGeneration;
    private final PasswordGenerator passwordGenerator;
    private final UserRepository userRepository;
    private final TrainerRepository trainerRepository;

    private final TraineeRepository traineeRepository;

    private static final Logger log = LoggerFactory.getLogger(TrainerService.class);




    @Autowired
    public TraineeService(NameGenerator nameGeneration, PasswordGenerator passwordGenerator,
                          UserRepository userRepository, TrainerRepository trainerRepository, TraineeRepository traineeRepository) {
        this.nameGeneration = nameGeneration;
        this.passwordGenerator = passwordGenerator;
        this.userRepository = userRepository;
        this.trainerRepository = trainerRepository;
        this.traineeRepository = traineeRepository;

    }


    @Override
    @Transactional
    public Trainee saveTrainee(Trainee trainee) {
        if (trainee == null || trainee.getUser() == null){
            throw new IllegalArgumentException("Trainee and associated User must not be null");
        }

        User user = trainee.getUser();

        String generatedUsername = nameGeneration.generateUniqueUsername(user, userRepository,
                traineeRepository.findAll(), trainerRepository.findAll());

        user.setUsername(generatedUsername);
        user.setPassword(passwordGenerator.generatePassword());
        user.setActive(true);

        trainee.setUser(user);
        return traineeRepository.save(trainee);
    //TODO DTO
    }

    @Override
    public boolean matchTrainerCredentials(String username, String password) {
        return AuthenticationUtils.matchCredentials(username, password, userRepository);
    }

    @Override
    public Optional<Trainee> findByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username must not be null or empty");
        }
        Optional<Trainee> traineeOptional = traineeRepository.findTraineeByUserUsername(username);

        if (traineeOptional.isEmpty()){
            log.debug("Trainer not found for username: {}", username);

        }
        return traineeOptional;
    }

    public void changeTraineePassword(String username, String newPassword) {
        Optional<Trainee> traineeOptional = traineeRepository.findTraineeByUserUsername(username);
        if (traineeOptional.isEmpty()) {
            throw new IllegalArgumentException("Trainee with username " + username + " not found");
        }

        Trainee trainee = traineeOptional.get();
        User user = trainee.getUser();
        if (user == null) {
            throw new IllegalStateException("User associated with trainee is null");
        }

        user.setPassword(newPassword);
        traineeRepository.save(trainee);
    }

    @Override
    public Trainee updateTraineeProfile(String username, Trainee updatedTrainee) {
        Optional<Trainee> traineeOptional = traineeRepository.findTraineeByUserUsername(username);

        if (traineeOptional.isEmpty()){
            throw new IllegalArgumentException("Trainee with username " + username + " not found");
        }

        Trainee trainee = traineeOptional.get();
        trainee.setBirthday(updatedTrainee.getBirthday());
        trainee.setAddress(updatedTrainee.getAddress());
        trainee.setTrainings(updatedTrainee.getTrainings());
        trainee.setTrainers(updatedTrainee.getTrainers());

        return traineeRepository.save(trainee);
    }

    @Override
    public void activateDeactivateTrainee(String username, boolean isActive) {
        Optional<Trainee> traineeOptional = traineeRepository.findTraineeByUserUsername(username);
        if (traineeOptional.isEmpty()) {
            throw new IllegalArgumentException("Trainee with username " + username + " not found");
        }
        Trainee trainee = traineeOptional.get();
        User user = trainee.getUser();

        if (user == null) {
            throw new IllegalStateException("User associated with trainee is null");
        }
        user.setActive(isActive);
        traineeRepository.save(trainee);
    }
    @Override
    public void deleteTrainer(String username) {
        Optional<Trainee> traineeOptional = traineeRepository.findTraineeByUserUsername(username);
        if (traineeOptional.isEmpty()) {
            throw new IllegalArgumentException("Trainee with username " + username + " not found");
        }
        Trainee trainee = traineeOptional.get();
        traineeRepository.delete(trainee);
    }


    @Override
    public Optional<Trainee> findById(long traineeId) {
        return Optional.empty();
    }

    @Override
    public Optional<Trainee> findByUserId(Long userId) {
        return Optional.empty();
    }







}
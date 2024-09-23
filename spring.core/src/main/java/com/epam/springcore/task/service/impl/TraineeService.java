package com.epam.springcore.task.service.impl;

import com.epam.springcore.task.repo.TraineeRepository;
import com.epam.springcore.task.repo.TrainingRepository;
import com.epam.springcore.task.repo.UserRepository;
import com.epam.springcore.task.dto.PassUsernameDTO;
import com.epam.springcore.task.dto.TraineeDTO;
import com.epam.springcore.task.dto.TrainerDTO;
import com.epam.springcore.task.dto.TrainingDTO;
import com.epam.springcore.task.mapper.TraineeMapper;
import com.epam.springcore.task.mapper.TrainerMapper;
import com.epam.springcore.task.mapper.TrainingMapper;
import com.epam.springcore.task.model.Trainee;
import com.epam.springcore.task.model.Trainer;
import com.epam.springcore.task.model.Training;
import com.epam.springcore.task.model.User;
import com.epam.springcore.task.service.ITraineeService;
import com.epam.springcore.task.utils.NameGenerator;
import com.epam.springcore.task.utils.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TraineeService implements ITraineeService {

    private final NameGenerator nameGeneration;
    private final PasswordGenerator passwordGenerator;
    private final UserRepository userRepository;
    private final TraineeRepository traineeRepository;
    private final TrainingRepository trainingRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public TraineeService(NameGenerator nameGeneration, PasswordGenerator passwordGenerator,
                          UserRepository userRepository,
                          TraineeRepository traineeRepository,
                          TrainingRepository trainingRepository, PasswordEncoder passwordEncoder) {
        this.nameGeneration = nameGeneration;
        this.passwordGenerator = passwordGenerator;
        this.userRepository = userRepository;
        this.traineeRepository = traineeRepository;
        this.trainingRepository = trainingRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public PassUsernameDTO saveTrainee(TraineeDTO traineeDTO) {

        if (traineeDTO == null || traineeDTO.getUser() == null) {
            throw new IllegalArgumentException("TraineeDTO and associated UserDTO must not be null");
        }

        Trainee trainee = TraineeMapper.INSTANCE.traineeToEntity(traineeDTO);

        User user = trainee.getUser();

        String generatedUsername = nameGeneration.generateUniqueUsername(user);

        String generatedPassword = passwordGenerator.generatePassword();

        user.setUsername(generatedUsername);
        String hashedPassword = passwordEncoder.encode(generatedPassword);
        user.setPassword(hashedPassword);
        user.setActive(true);
        trainee.setUser(user);
        traineeRepository.save(trainee);

        return new PassUsernameDTO(generatedUsername, generatedPassword);
    }

    @Override
    public boolean matchTraineeCredentials(PassUsernameDTO passUsernameDTO) {
        Optional<User> optionalUser = userRepository.findByUsername(passUsernameDTO.getUsername());
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            return passwordEncoder.matches(passUsernameDTO.getPassword(), user.getPassword());
        }
        return false;
    }

    @Override
    public Optional<TraineeDTO> findByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username must not be null or empty");
        }

        Optional<Trainee> traineeOptional = traineeRepository.findTraineeByUserUsername(username);

        if (traineeOptional.isEmpty()){
            return Optional.empty();
        }

        TraineeDTO traineeDTO = TraineeMapper.INSTANCE.traineeToDTO(traineeOptional.get());
        return Optional.of(traineeDTO);
    }

    @Override
    @Transactional
    public PassUsernameDTO changeTraineePassword(PassUsernameDTO passUsernameDTO) {
        Optional<Trainee> traineeOptional = traineeRepository.findTraineeByUserUsername(passUsernameDTO.getUsername());
        if (traineeOptional.isEmpty()) {
            throw new IllegalArgumentException("Trainee with username " + passUsernameDTO.getUsername() + " not found");
        }

        Trainee trainee = traineeOptional.get();
        User user = trainee.getUser();
        if (user == null) {
            throw new IllegalStateException("User associated with trainee is null");
        }

        String hashedPassword = passwordEncoder.encode(passUsernameDTO.getPassword());

        user.setPassword(hashedPassword);
        traineeRepository.save(trainee);
        return passUsernameDTO;
    }

    @Override
    @Transactional
    public TraineeDTO updateTraineeProfile(String username, TraineeDTO updatedTraineeDTO) {
        Optional<Trainee> traineeOptional = traineeRepository.findTraineeByUserUsername(username);

        if (traineeOptional.isEmpty()) {
            throw new IllegalArgumentException("Trainee with username " + username + " not found");
        }

        Trainee trainee = traineeOptional.get();
        trainee.setBirthday(updatedTraineeDTO.getBirthday());
        trainee.setAddress(updatedTraineeDTO.getAddress());
        trainee.setTrainings(TrainingMapper.INSTANCE.dtoListToEntityList(updatedTraineeDTO.getTrainings()));
        trainee.setTrainers(TrainerMapper.INSTANCE.dtoListToEntitySet(updatedTraineeDTO.getTrainers()));

        Trainee updatedTrainee = traineeRepository.save(trainee);

        return TraineeMapper.INSTANCE.traineeToDTO(updatedTrainee);
    }

    @Override
    @Transactional
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
    @Transactional
    public void deleteTrainee(String username) {
        Optional<Trainee> traineeOptional = traineeRepository.findTraineeByUserUsername(username);
        if (traineeOptional.isEmpty()) {
            throw new IllegalArgumentException("Trainee with username " + username + " not found");
        }
        Trainee trainee = traineeOptional.get();
        traineeRepository.delete(trainee);
    }

    @Transactional
    @Override
    public List<TrainingDTO> getTraineeTrainingsByCriteria(String traineeUsername, LocalDate fromDate, LocalDate toDate,
                                                           String trainerUsername, String trainingName) {
        List<Training> trainings = trainingRepository.
                findByTrainee_User_UsernameAndDateBetweenAndTrainer_User_UsernameAndTrainingType_Name(
                traineeUsername, fromDate, toDate, trainerUsername, trainingName);
        return TrainingMapper.INSTANCE.entityListToDTOList(trainings);
    }

    @Override
    @Transactional
    public Trainee updateTraineeTrainers(String traineeUsername, Set<TrainerDTO> newTrainerDTOs) {
        Optional<Trainee> traineeOptional = traineeRepository.findTraineeByUserUsername(traineeUsername);
        if (traineeOptional.isEmpty()) {
            throw new IllegalArgumentException("Trainee with username " + traineeUsername + " not found");
        }
        Trainee trainee = traineeOptional.get();
        Set<Trainer> newTrainers =
                new HashSet<>(TrainerMapper.INSTANCE.dtoListToEntityList(new ArrayList<>(newTrainerDTOs)));
        trainee.setTrainers(newTrainers);

        return traineeRepository.save(trainee);
    }

    @Override
    public Optional<TraineeDTO> findById(long traineeId) {
        Optional<Trainee> traineeOptional = traineeRepository.findById(traineeId);
        return traineeOptional.map(TraineeMapper.INSTANCE::traineeToDTO);
    }

    @Override
    public Optional<TraineeDTO> findByUserId(Long userId) {
        Optional<Trainee> traineeOptional = traineeRepository.findTraineeByUserUserId(userId);
        return traineeOptional.map(TraineeMapper.INSTANCE::traineeToDTO);
    }

}

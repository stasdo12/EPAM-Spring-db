package com.epam.springcore.task.service.impl;

import com.epam.springcore.task.dao.TraineeDAO;
import com.epam.springcore.task.dao.TrainerDAO;
import com.epam.springcore.task.model.Trainer;
import com.epam.springcore.task.model.User;
import com.epam.springcore.task.service.TrainerService;
import com.epam.springcore.task.utils.NameGenerator;
import com.epam.springcore.task.utils.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainerServiceImpl implements TrainerService {

    private TrainerDAO trainerDAO;
    private TraineeDAO traineeDAO;
    private PasswordGenerator passwordGenerator;
    private NameGenerator nameGenerator;


    @Autowired
    public TrainerServiceImpl(TrainerDAO trainerDAO, TraineeDAO traineeDAO, PasswordGenerator passwordGenerator, NameGenerator nameGenerator) {
        this.trainerDAO = trainerDAO;
        this.traineeDAO = traineeDAO;
        this.passwordGenerator = passwordGenerator;
        this.nameGenerator = nameGenerator;
    }

    @Override
    public Optional<Trainer> create(Trainer trainer) {
        long maxId = trainerDAO.getMaxId();
        trainer.setTrainerId(maxId);

        User user = trainer.getUser();

        if (user != null){
            user.setUserName(nameGenerator.generateUsername(user, traineeDAO.getAllTrainees(),
                    trainerDAO.getAllTrainers()));
            user.setPassword(passwordGenerator.generatePassword());
        }else {
            throw new IllegalArgumentException("User must not be null in Trainer");
        }
        return trainerDAO.create(maxId, trainer);
    }

    @Override
    public Optional<Trainer> update(Trainer trainer) {
        return trainerDAO.update(trainer);
    }

    @Override
    public Optional<Trainer> getById(long trainerId) {
        return trainerDAO.getById(trainerId);
    }

    @Override
    public Optional<Trainer> getByUsername(String username) {
        return trainerDAO.getByUsername(username);
    }

    @Override
    public List<Trainer> getAllTrainers() {
        return trainerDAO.getAllTrainers();
    }
}

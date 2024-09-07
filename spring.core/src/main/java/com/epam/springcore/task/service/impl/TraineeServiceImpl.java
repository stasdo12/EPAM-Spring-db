package com.epam.springcore.task.service.impl;

import com.epam.springcore.task.dao.TraineeDAO;
import com.epam.springcore.task.dao.TrainerDAO;
import com.epam.springcore.task.model.Trainee;
import com.epam.springcore.task.model.User;
import com.epam.springcore.task.service.TraineeService;
import com.epam.springcore.task.utils.NameGenerator;
import com.epam.springcore.task.utils.PasswordGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TraineeServiceImpl implements TraineeService {

    private static final Logger logger = LoggerFactory.getLogger(TraineeServiceImpl.class);

    private final TraineeDAO traineeDAO;

    private final TrainerDAO trainerDAO;

    private final PasswordGenerator passwordGenerator;

    private final NameGenerator nameGenerator;


    @Autowired
    public TraineeServiceImpl(TraineeDAO traineeDAO, TrainerDAO trainerDAO,
                              PasswordGenerator passwordGenerator, NameGenerator nameGenerator) {
        this.traineeDAO = traineeDAO;
        this.trainerDAO = trainerDAO;
        this.passwordGenerator = passwordGenerator;
        this.nameGenerator = nameGenerator;
    }

    @Override
    public Optional<Trainee> create(Trainee trainee) {
        long maxId = traineeDAO.getMaxId();
        trainee.setTraineeId(maxId);

        User user = trainee.getUser();

       if (user != null){
           user.setUserName(nameGenerator.generateUsername(user, traineeDAO.getAllTrainees(), trainerDAO.getAllTrainers()));
           user.setPassword(passwordGenerator.generatePassword());
       }else {
           throw new IllegalArgumentException("User must not be null in Trainee");
       }
       return traineeDAO.create(maxId, trainee);
    }

    @Override
    public Optional<Trainee> update(Trainee trainee) {
        return traineeDAO.update(trainee);
    }

    @Override
    public boolean delete(long traineeId) {
        return traineeDAO.deleteById(traineeId);
    }

    @Override
    public Optional<Trainee> getById(long traineeId) {
        return traineeDAO.findById(traineeId);
    }

    @Override
    public Optional<Trainee> getByUsername(String username) {
        return traineeDAO.findByUsername(username);
    }

    @Override
    public List<Trainee> getTrainees() {
        return traineeDAO.getAllTrainees();
    }

}

package com.epam.springcore.task.service.impl;


import com.epam.springcore.task.dao.TrainerRepository;
import com.epam.springcore.task.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainerService {


    private final UserRepository userRepository;


    private final TrainerRepository trainerRepository;


    @Autowired
    public TrainerService(UserRepository userRepository, TrainerRepository trainerRepository) {
        this.userRepository = userRepository;
        this.trainerRepository = trainerRepository;
    }

}

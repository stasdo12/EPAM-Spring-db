package com.epam.springcore.task.utils;

import com.epam.springcore.task.model.Trainee;
import com.epam.springcore.task.model.Trainer;
import com.epam.springcore.task.model.User;

import java.util.List;

public interface NameGenerator {

    String generateUsername(User user);
    String generateUsername(User user, List<Trainee> trainees, List<Trainer> trainers);

}

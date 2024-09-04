package com.epam.springcore.task.utils.impl;

import com.epam.springcore.task.model.Trainee;
import com.epam.springcore.task.model.Trainer;
import com.epam.springcore.task.model.User;
import com.epam.springcore.task.utils.NameGenerator;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;


@Component
public class NameGenerationImpl implements NameGenerator {
    @Override
    public String generateUsername(User user) {
        return user.getFirstName() + "." + user.getLastName();
    }

    @Override
    public String generateUsername(User user, List<Trainee> trainees, List<Trainer> trainers) {
        String baseUsername = generateUsername(user);

        Stream<User> users = Stream.concat(
                trainees.stream().map(Trainee::getUser),
                trainers.stream().map(Trainer::getUser)
        );

        int maxIndex = users.map(User::getUserName)
                .map(userName -> userName.replaceAll("\\D+?(\\d+)$", "$1"))
                .filter(s -> !s.isEmpty())
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(0);

        return baseUsername + (maxIndex + 1);
    }
}

package com.epam.springcore.task.utils.impl;

import com.epam.springcore.task.dao.UserRepository;
import com.epam.springcore.task.model.Trainee;
import com.epam.springcore.task.model.Trainer;
import com.epam.springcore.task.model.User;
import com.epam.springcore.task.utils.NameGenerator;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.OptionalInt;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;


@Component
public class NameGenerationImpl implements NameGenerator {

    private static final Pattern NUMBER_PATTERN = Pattern.compile("(\\d+)$");

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

        OptionalInt maxIndex =  users.map(User::getUsername)
                .map(userName -> {
                    Matcher matcher = NUMBER_PATTERN.matcher(userName);
                    return matcher.find() ? matcher.group(1) : "";
                })
                .filter(s -> !s.isEmpty())
                .mapToInt(Integer::parseInt)
                .max();

        int nextIndex = maxIndex.orElse(0) + 1;

        return baseUsername + nextIndex;
    }

    public String generateUniqueUsername(User user, UserRepository userRepository,
                                         List<Trainee> trainees, List<Trainer> trainers) {
        String baseUsername = generateUsername(user);

        if (!userRepository.existsByUsername(baseUsername)) {
            return baseUsername;
        }

        return generateUsername(user, trainees, trainers);
    }
}

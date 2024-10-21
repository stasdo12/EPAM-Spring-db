package com.epam.springcore.task.utils.impl;

import com.epam.springcore.task.repo.UserRepository;
import com.epam.springcore.task.model.User;
import com.epam.springcore.task.utils.NameGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.OptionalInt;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



@Component
public class NameGenerationImpl implements NameGenerator {
    private final UserRepository userRepository;
    private static final Pattern NUMBER_PATTERN = Pattern.compile("(\\d+)$");

    @Autowired
    public NameGenerationImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String generateUsername(User user) {
        return user.getFirstName() + "." + user.getLastName();
    }

    @Override
    public String generateUniqueUsername(User user) {
        String baseUsername = generateUsername(user);

        List<User> takenUsers = userRepository.findByUsernameStartingWith(baseUsername);

        if (takenUsers.isEmpty()) {
            return baseUsername;
        }
        OptionalInt maxIndex = takenUsers.stream()
                .map(User::getUsername)
                .map(username -> {
                    Matcher matcher = NUMBER_PATTERN.matcher(username);
                    return matcher.find() ? matcher.group(1) : "";
                })
                .filter(s -> !s.isEmpty())
                .mapToInt(Integer::parseInt)
                .max();

        int nextIndex = maxIndex.orElse(0) + 1;

        return baseUsername + nextIndex;
    }

}
package com.epam.springcore.task.utils.impl;

import com.epam.springcore.task.dao.UserRepository;
import com.epam.springcore.task.model.User;

import java.util.Optional;

public class AuthenticationUtils {

    public static boolean matchCredentials(String username, String password, UserRepository userRepository) {
        if (username == null || password == null) {
            throw new IllegalArgumentException("Username and password must not be null");
        }

        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return user.getPassword().equals(password);
        }
        return false;
    }
}

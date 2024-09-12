package com.epam.springcore.task.service.impl;


import com.epam.springcore.task.dao.UserRepository;
import com.epam.springcore.task.model.User;
import com.epam.springcore.task.service.IUserService;
import com.epam.springcore.task.utils.impl.NameGenerationImpl;
import com.epam.springcore.task.utils.impl.PasswordGenerationImpl;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordGenerationImpl passwordGeneration;
    private final NameGenerationImpl nameGeneration;

    public UserService(UserRepository userRepository, PasswordGenerationImpl passwordGeneration, NameGenerationImpl nameGeneration) {
        this.userRepository = userRepository;
        this.passwordGeneration = passwordGeneration;
        this.nameGeneration = nameGeneration;
    }


    @Override
    public User registerUser(User user) {
        if(user == null){
            throw new IllegalArgumentException("User must not be null");
        }
        user.setUsername(nameGeneration.generateUsername(user));
        user.setPassword(passwordGeneration.generatePassword());
        user.setActive(true);

        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        if (username == null){
            throw new IllegalArgumentException("Username must not be null");
        }
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findById(Long userId) {
        if (userId == null){
            throw new IllegalArgumentException("UserId must not be null");
        }
        return userRepository.findById(userId);
    }

    public boolean matchUsernameAndPassword(String username, String password) {
        if (username == null || password == null) {
            throw new IllegalArgumentException("Username and password must not be null");
        }
        User user = findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return password.equals(user.getPassword());
    }
}

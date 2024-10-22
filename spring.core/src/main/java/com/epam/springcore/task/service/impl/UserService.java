package com.epam.springcore.task.service.impl;

import com.epam.springcore.task.dto.PassUsernameDTO;
import com.epam.springcore.task.model.User;
import com.epam.springcore.task.repo.UserRepository;
import com.epam.springcore.task.service.IUserService;
import com.epam.springcore.task.utils.NameGenerator;
import com.epam.springcore.task.utils.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService{
    private final UserRepository userRepository;
    private final NameGenerator nameGenerator;
    private final PasswordGenerator passwordGenerator;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       NameGenerator nameGenerator,
                       PasswordGenerator passwordGenerator,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.nameGenerator = nameGenerator;
        this.passwordGenerator = passwordGenerator;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public PassUsernameDTO generateAndSaveUser(User user) {
        String generatedUsername = nameGenerator.generateUniqueUsername(user);
        String generatedPassword = passwordGenerator.generatePassword();
        user.setUsername(generatedUsername);
        String hashedPassword = passwordEncoder.encode(generatedPassword);
        user.setPassword(hashedPassword);
        user.setActive(true);
        userRepository.save(user);
        return new PassUsernameDTO(generatedUsername, generatedPassword);
    }

    @Override
    public boolean matchUserCredentials(PassUsernameDTO passUsernameDTO) {
        Optional<User> optionalUser = userRepository.findByUsername(passUsernameDTO.getUsername());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return passwordEncoder.matches(passUsernameDTO.getPassword(), user.getPassword());
        }
        return false;
    }

    @Override
    public void changeUserPassword(String username, String newPassword) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        }
    }

    @Override
    public void activateDeactivateUser(String username, boolean isActive) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setActive(isActive);
            userRepository.save(user);
        }
    }

}

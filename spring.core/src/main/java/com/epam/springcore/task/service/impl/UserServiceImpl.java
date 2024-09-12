package com.epam.springcore.task.service.impl;


import com.epam.springcore.task.model.User;
import com.epam.springcore.task.repo.UserRepository;
import com.epam.springcore.task.utils.impl.NameGenerationImpl;
import com.epam.springcore.task.utils.impl.PasswordGenerationImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final NameGenerationImpl nameGenerator;
    private final PasswordGenerationImpl passwordGenerator;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, NameGenerationImpl nameGenerator,
                           PasswordGenerationImpl passwordGenerator) {
        this.userRepository = userRepository;
        this.nameGenerator = nameGenerator;
        this.passwordGenerator = passwordGenerator;
    }

    @Override
    public User createTrainerProfile(User user) {
        user.setUserName(nameGenerator.generateUsername(user));
        user.setPassword(passwordGenerator.generatePassword());
        return userRepository.save(user);
    }

    @Override
    public User createTraineeProfile(User user) {
        user.setUserName(nameGenerator.generateUsername(user));
        user.setPassword(passwordGenerator.generatePassword());
        return userRepository.save(user);
    }

    @Override
    public User findByUserName(String username) {
        return userRepository.findByUserName(username);
    }

    @Override
    public boolean checkoutCredentials(String username, String password) {
        User user = findByUserName(username);
        return user != null && user.getPassword().equals(password);
    }

    @Override
    public void changePassword(String username, String newPassword) {
        User user = findByUserName(username);
        if (user != null){
            user.setPassword(newPassword);
            userRepository.save(user);
        }

    }

    @Override
    public void updateUserProfile(User user) {
        User existingUser = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + user.getUserId()));

        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setUserName(user.getUserName());
        existingUser.setPassword(user.getPassword());
        existingUser.setActive(user.isActive());

        userRepository.save(existingUser);
    }

    @Override
    public void activateUser(String username) {
        User user = findByUserName(username);
        if (user != null){
            user.setActive(true);
            userRepository.save(user);
        }
    }

    @Override
    public void deactivateUser(String username) {
        User user = findByUserName(username);
        if (user != null){
            user.setActive(false);
            userRepository.save(user);
        }
    }

    @Override
    public void deleteUserByUsername(String username) {
        User user = findByUserName(username);
        if (user != null){
            userRepository.delete(user);
        }
    }
}

package com.epam.springcore.task.service.impl;


import com.epam.springcore.task.model.User;

public interface UserService {

    User createTrainerProfile(User user);

    User createTraineeProfile(User user);

    User findByUserName(String username);

    boolean checkoutCredentials(String username, String password);

    void changePassword(String username, String newPassword);

    void updateUserProfile(User user);

    void activateUser(String username);

    void deactivateUser(String username);

    void deleteUserByUsername(String username);


}

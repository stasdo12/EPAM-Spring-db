package com.epam.springcore.task.service;

import com.epam.springcore.task.model.User;

import java.util.Optional;

public interface IUserService {

    User registerUser(User user);

    Optional<User> findByUsername(String username);

    Optional<User> findById(Long userId);

}

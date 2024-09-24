package com.epam.springcore.task.utils;

import com.epam.springcore.task.model.User;


public interface NameGenerator {

    String generateUsername(User user);

    String generateUniqueUsername(User user);
}

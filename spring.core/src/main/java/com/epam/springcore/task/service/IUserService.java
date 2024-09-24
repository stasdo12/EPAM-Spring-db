package com.epam.springcore.task.service;

import com.epam.springcore.task.dto.PassUsernameDTO;
import com.epam.springcore.task.model.User;

public interface IUserService {

    PassUsernameDTO generateAndSaveUser(User user);

    boolean matchUserCredentials(PassUsernameDTO passUsernameDTO);

    void changeUserPassword(String username, String newPassword);

    void activateDeactivateUser(String username, boolean isActive);

}

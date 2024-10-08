package com.epam.springcore.task.controller.impl;

import com.epam.springcore.task.controller.IUserController;
import com.epam.springcore.task.dto.PassUsernameDTO;
import com.epam.springcore.task.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class UserController  implements IUserController {

    private final UserService userService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Override
    public void login(PassUsernameDTO passUsernameDTO) {
        userService.matchUserCredentials(passUsernameDTO);
    }

    @PutMapping("/update-password")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public void changePassword(PassUsernameDTO passUsernameDTO) {
        userService.changeUserPassword(passUsernameDTO.getUsername(), passUsernameDTO.getPassword());
    }
}

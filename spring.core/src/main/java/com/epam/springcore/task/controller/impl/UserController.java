package com.epam.springcore.task.controller.impl;

import com.epam.springcore.task.controller.IUserController;
import com.epam.springcore.task.dto.PassUsernameDTO;
import com.epam.springcore.task.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class UserController  implements IUserController {

    private final UserService userService;

    @PostMapping
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

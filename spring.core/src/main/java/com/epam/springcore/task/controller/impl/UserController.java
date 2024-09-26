package com.epam.springcore.task.controller.impl;

import com.epam.springcore.task.controller.IUserController;
import com.epam.springcore.task.dto.PassUsernameDTO;
import com.epam.springcore.task.facade.GymFacade;
import com.epam.springcore.task.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor

public class UserController  implements IUserController {

    private final UserService userService;


    @Override
    public ResponseEntity<Void> login(PassUsernameDTO passUsernameDTO) {
        userService.matchUserCredentials(passUsernameDTO);
        return ResponseEntity.ok().build();

    }

    @Override
    public ResponseEntity<Void> changePassword(PassUsernameDTO passUsernameDTO) {
        userService.changeUserPassword(passUsernameDTO.getUsername(), passUsernameDTO.getPassword());
        return ResponseEntity.ok().build();
    }
}

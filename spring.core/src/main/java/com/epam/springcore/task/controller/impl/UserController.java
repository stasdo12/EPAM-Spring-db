package com.epam.springcore.task.controller.impl;

import com.epam.springcore.task.controller.IUserController;
import com.epam.springcore.task.dto.PassUsernameDTO;
import com.epam.springcore.task.facade.GymFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor

public class UserController  implements IUserController {

    private final GymFacade gymFacade;


    @Override
    public ResponseEntity<Void> login(PassUsernameDTO passUsernameDTO) {
        gymFacade.matchTraineeCredentials(passUsernameDTO);
        return ResponseEntity.ok().build();

    }
}

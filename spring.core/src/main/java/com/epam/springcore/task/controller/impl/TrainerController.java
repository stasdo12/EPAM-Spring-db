package com.epam.springcore.task.controller.impl;

import com.epam.springcore.task.dto.PassUsernameDTO;
import com.epam.springcore.task.dto.TrainerDTO;
import com.epam.springcore.task.facade.GymFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TrainerController {

    private final GymFacade gymFacade;

    @PostMapping("/register")
    public ResponseEntity<Object> registerTrainer(@RequestBody TrainerDTO trainerDTO){
        PassUsernameDTO response = gymFacade.saveTrainer(trainerDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}

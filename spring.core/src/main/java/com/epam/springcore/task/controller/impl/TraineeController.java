package com.epam.springcore.task.controller.impl;

import com.epam.springcore.task.dto.PassUsernameDTO;
import com.epam.springcore.task.dto.TraineeDTO;
import com.epam.springcore.task.facade.GymFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/trainees")
@RequiredArgsConstructor
public class TraineeController {


    private final GymFacade gymFacade;


    @PostMapping("/register")
    public ResponseEntity<Object> registerTrainee(@RequestBody TraineeDTO traineeDTO){
            PassUsernameDTO response = gymFacade.saveTrainee(traineeDTO);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}

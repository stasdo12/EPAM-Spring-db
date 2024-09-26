package com.epam.springcore.task.controller.impl;

import com.epam.springcore.task.controller.ITraineeController;
import com.epam.springcore.task.dto.PassUsernameDTO;
import com.epam.springcore.task.dto.TraineeDTO;
import com.epam.springcore.task.facade.GymFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TraineeController implements ITraineeController {


    private final GymFacade gymFacade;


    @Override
    public ResponseEntity<Object> registerTrainee(@RequestBody TraineeDTO traineeDTO){
            PassUsernameDTO response = gymFacade.saveTrainee(traineeDTO);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
    }




}

package com.epam.springcore.task.controller.impl;

import com.epam.springcore.task.controller.ITraineeController;
import com.epam.springcore.task.dto.PassUsernameDTO;
import com.epam.springcore.task.dto.TraineeDTO;
import com.epam.springcore.task.dto.TrainerDTO;
import com.epam.springcore.task.facade.GymFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class TraineeController implements ITraineeController {


    private final GymFacade gymFacade;


    @Override
    public ResponseEntity<Object> registerTrainee(@RequestBody TraineeDTO traineeDTO){
            PassUsernameDTO response = gymFacade.saveTrainee(traineeDTO);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<TraineeDTO> getTraineeProfileByUsername(String username) {
        Optional<TraineeDTO> traineeDTOOptional = gymFacade.findTraineeByUsername(username);
        return traineeDTOOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @Override
    public ResponseEntity<TraineeDTO> updateTraineeProfile(String username, TraineeDTO trainee) {
        TraineeDTO updatedTrainee = gymFacade.updateTraineeProfile(username, trainee);
        return ResponseEntity.ok(updatedTrainee);
    }

    @Override
    public ResponseEntity<Void> deleteTrainee(String username) {
        gymFacade.deleteTrainee(username);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<TrainerDTO>> getNotAssignedActiveTrainers(String username) {
        return ResponseEntity.ok(gymFacade.getTrainersNotAssignedToTrainee(username));
    }


}

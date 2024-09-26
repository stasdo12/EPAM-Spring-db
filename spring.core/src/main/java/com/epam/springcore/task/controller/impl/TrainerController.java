package com.epam.springcore.task.controller.impl;

import com.epam.springcore.task.controller.ITrainerController;
import com.epam.springcore.task.dto.PassUsernameDTO;
import com.epam.springcore.task.dto.TrainerDTO;
import com.epam.springcore.task.facade.GymFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class TrainerController implements ITrainerController {

    private final GymFacade gymFacade;


    @Override
    @PostMapping("/register")
    public ResponseEntity<Object> registerTrainer(@RequestBody TrainerDTO trainerDTO){
        PassUsernameDTO response = gymFacade.saveTrainer(trainerDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<TrainerDTO> getTrainerProfileByUsername(String username) {
        Optional<TrainerDTO> trainerDTOOptional = gymFacade.findTrainerByUsername(username);
        return trainerDTOOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<TrainerDTO> updateTrainerProfile(String username, TrainerDTO updatedTrainerDTO) {
        TrainerDTO updatedTrainer = gymFacade.updateTrainerProfile(username, updatedTrainerDTO);
        return ResponseEntity.ok(updatedTrainer);
    }

}

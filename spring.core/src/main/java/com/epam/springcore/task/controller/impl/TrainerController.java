package com.epam.springcore.task.controller.impl;

import com.epam.springcore.task.controller.ITrainerController;
import com.epam.springcore.task.dto.TrainerDTO;
import com.epam.springcore.task.dto.TrainingDTO;
import com.epam.springcore.task.facade.GymFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trainers")
public class TrainerController implements ITrainerController {

    private final GymFacade gymFacade;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public void registerTrainer(@RequestBody TrainerDTO trainerDTO){
      gymFacade.saveTrainer(trainerDTO);
    }

    @GetMapping("/{username}")
    @Override
    public ResponseEntity<TrainerDTO> getTrainerProfileByUsername(@PathVariable String username) {
        Optional<TrainerDTO> trainerDTOOptional = gymFacade.findTrainerByUsername(username);
        return trainerDTOOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{username}")
    @Override
    public ResponseEntity<TrainerDTO> updateTrainerProfile(@PathVariable String username, TrainerDTO updatedTrainerDTO) {
        TrainerDTO updatedTrainer = gymFacade.updateTrainerProfile(username, updatedTrainerDTO);
        return ResponseEntity.ok(updatedTrainer);
    }

    @GetMapping("/{username}/trainings")
    @Override
    public ResponseEntity<List<TrainingDTO>> getTrainerTrainings(@PathVariable String username,
                                                                 LocalDate from,
                                                                 LocalDate to,
                                                                 String traineeUsername,
                                                                 String trainingName) {
        List<TrainingDTO> trainings = gymFacade.getTrainerTrainingsByCriteria(username, from, to, traineeUsername,
                trainingName);
        return ResponseEntity.ok(trainings);

    }

    @PatchMapping("/{username}/activate")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public void activateDeactivateTrainee(@PathVariable String username, boolean isActive) {
        gymFacade.activateDeactivateTrainer(username, isActive);
    }
}

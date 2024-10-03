package com.epam.springcore.task.controller.impl;

import com.epam.springcore.task.controller.ITrainingController;
import com.epam.springcore.task.dto.TrainingDTO;
import com.epam.springcore.task.facade.GymFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trainings")
public class TrainingController implements ITrainingController {

    private final GymFacade gymFacade;

    @PostMapping
    @Override
    public ResponseEntity<Void> addTraining(TrainingDTO trainingDTO) {
        gymFacade.addTraining(trainingDTO);
        return ResponseEntity.ok().build();
    }
}

package com.epam.springcore.task.controller.impl;

import com.epam.springcore.task.controller.ITrainingController;
import com.epam.springcore.task.dto.TrainingDTO;
import com.epam.springcore.task.facade.GymFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trainings")
public class TrainingController implements ITrainingController {

    private final GymFacade gymFacade;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @Override
    public void addTraining(TrainingDTO trainingDTO) {
        gymFacade.addTraining(trainingDTO);
    }
}

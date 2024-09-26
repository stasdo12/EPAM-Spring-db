package com.epam.springcore.task.controller.impl;

import com.epam.springcore.task.controller.ITrainingTypeController;
import com.epam.springcore.task.dto.TrainingTypeDTO;
import com.epam.springcore.task.service.impl.TrainingTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TrainingTypeController  implements ITrainingTypeController {

    private final TrainingTypeService trainingTypeService;
    @Override
    public ResponseEntity<List<TrainingTypeDTO>> getTrainingTypes() {
        List<TrainingTypeDTO> trainingTypes = trainingTypeService.getAll();
        return ResponseEntity.ok(trainingTypes);
    }
}

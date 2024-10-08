package com.epam.springcore.task.service;

import com.epam.springcore.task.dto.TrainingTypeDTO;

import java.util.List;

public interface ITrainingTypeService {

    List<TrainingTypeDTO> getAll();
}

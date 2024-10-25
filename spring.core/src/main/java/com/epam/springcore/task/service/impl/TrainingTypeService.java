package com.epam.springcore.task.service.impl;

import com.epam.springcore.task.dto.TrainingTypeDTO;
import com.epam.springcore.task.mapper.TrainingTypeMapper;
import com.epam.springcore.task.model.TrainingType;
import com.epam.springcore.task.repo.TrainingTypeRepository;
import com.epam.springcore.task.service.ITrainingTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingTypeService implements ITrainingTypeService {

    private final TrainingTypeRepository trainingTypeRepository;
    private final TrainingTypeMapper trainingTypeMapper;

    @Override
    public List<TrainingTypeDTO> getAll() {
        List<TrainingType> trainingTypes = trainingTypeRepository.findAll();
        return trainingTypes.stream()
                .map(trainingTypeMapper::trainingTypeToDTO)
                .toList();
    }

}

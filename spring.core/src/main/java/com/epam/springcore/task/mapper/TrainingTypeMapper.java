package com.epam.springcore.task.mapper;

import com.epam.springcore.task.dto.TrainingTypeDTO;
import com.epam.springcore.task.model.TrainingType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TrainingTypeMapper {

    TrainingTypeDTO trainingTypeToDTO(TrainingType trainingType);

    @Mapping(target = "id", ignore = true)
    TrainingType trainingTypeToEntity(TrainingTypeDTO trainingTypeDTO);
}

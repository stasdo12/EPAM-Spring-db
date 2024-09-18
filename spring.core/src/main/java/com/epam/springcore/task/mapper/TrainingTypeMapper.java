package com.epam.springcore.task.mapper;

import com.epam.springcore.task.dto.TrainingTypeDTO;
import com.epam.springcore.task.model.TrainingType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

//@Mapper(uses = {TraineeMapper.class, TrainerMapper.class, TrainingTypeMapper.class})
@Mapper
public interface TrainingTypeMapper {

    TrainingTypeMapper INSTANCE = Mappers.getMapper(TrainingTypeMapper.class);

    TrainingTypeDTO trainingTypeToDTO(TrainingType trainingType);

    @Mapping(target = "id", ignore = true)
    TrainingType trainingTypeToEntity(TrainingTypeDTO trainingTypeDTO);
}

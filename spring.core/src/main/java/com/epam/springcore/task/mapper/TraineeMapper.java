package com.epam.springcore.task.mapper;

import com.epam.springcore.task.dto.TraineeDTO;
import com.epam.springcore.task.model.Trainee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TraineeMapper {

    TraineeMapper INSTANCE = Mappers.getMapper(TraineeMapper.class);

    TraineeDTO traineeToDTO(Trainee trainee);

    @Mapping(target = "traineeId", ignore = true)
    Trainee traineeToEntity(TraineeDTO traineeDTO);
}

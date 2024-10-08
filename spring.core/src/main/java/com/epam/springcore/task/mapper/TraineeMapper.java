package com.epam.springcore.task.mapper;

import com.epam.springcore.task.dto.TraineeDTO;
import com.epam.springcore.task.model.Trainee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TraineeMapper {

    @Mapping(target = "trainers", ignore = true)
    TraineeDTO traineeToDTO(Trainee trainee);

    @Mapping(target = "traineeId", ignore = true)
    Trainee traineeToEntity(TraineeDTO traineeDTO);
}

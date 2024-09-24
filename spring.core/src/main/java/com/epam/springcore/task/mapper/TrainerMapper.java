package com.epam.springcore.task.mapper;

import com.epam.springcore.task.dto.TrainerDTO;
import com.epam.springcore.task.model.Trainer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface TrainerMapper {

    TrainerDTO trainerToDTO(Trainer trainer);

    @Mapping(target = "trainerId", ignore = true)
    Trainer trainerToEntity(TrainerDTO trainerDTO);
}

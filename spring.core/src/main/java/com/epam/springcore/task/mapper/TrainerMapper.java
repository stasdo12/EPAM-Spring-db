package com.epam.springcore.task.mapper;

import com.epam.springcore.task.dto.TrainerDTO;
import com.epam.springcore.task.model.Trainer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {UserMapper.class, TrainingTypeMapper.class, TrainingMapper.class, TraineeMapper.class})
public interface TrainerMapper {

    TrainerMapper INSTANCE = Mappers.getMapper(TrainerMapper.class);

    @Mapping(source = "user", target = "user")
    @Mapping(source = "specialization", target = "specialization")
    @Mapping(source = "trainings", target = "trainings")
    @Mapping(source = "trainees", target = "trainees")
    TrainerDTO trainerToDTO(Trainer trainer);

    @Mapping(source = "user", target = "user")
    @Mapping(source = "specialization", target = "specialization")
    @Mapping(source = "trainings", target = "trainings")
    @Mapping(source = "trainees", target = "trainees")
    Trainer trainerToEntity(TrainerDTO trainerDTO);
}

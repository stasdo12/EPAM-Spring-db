package com.epam.springcore.task.mapper;


import com.epam.springcore.task.dto.TraineeDTO;
import com.epam.springcore.task.model.Trainee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {UserMapper.class, TrainingMapper.class, TrainerMapper.class})
public interface TraineeMapper {

    TraineeMapper INSTANCE = Mappers.getMapper(TraineeMapper.class);

    @Mapping(source = "user", target = "user")
    @Mapping(source = "trainings", target = "trainings")
    @Mapping(source = "trainers", target = "trainers")
    TraineeDTO traineeToDTO(Trainee trainee);

    @Mapping(source = "user", target = "user")
    @Mapping(source = "trainings", target = "trainings")
    @Mapping(source = "trainers", target = "trainers")
    Trainee traineeToEntity(TraineeDTO traineeDTO);
}

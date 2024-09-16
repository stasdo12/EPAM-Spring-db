package com.epam.springcore.task.mapper;

import com.epam.springcore.task.dto.TrainingDTO;
import com.epam.springcore.task.model.Training;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {TraineeMapper.class, TrainerMapper.class, TrainingTypeMapper.class})
public interface TrainingMapper {

    TrainingMapper INSTANCE = Mappers.getMapper(TrainingMapper.class);

    @Mapping(source = "trainee", target = "trainee")
    @Mapping(source = "trainer", target = "trainer")
    @Mapping(source = "trainingName", target = "trainingName")
    @Mapping(source = "trainingType", target = "trainingType")
    @Mapping(source = "date", target = "date")
    @Mapping(source = "durationMinutes", target = "durationMinutes")
    TrainingDTO trainingToDTO(Training training);

    @Mapping(source = "trainee", target = "trainee")
    @Mapping(source = "trainer", target = "trainer")
    @Mapping(source = "trainingName", target = "trainingName")
    @Mapping(source = "trainingType", target = "trainingType")
    @Mapping(source = "date", target = "date")
    @Mapping(source = "durationMinutes", target = "durationMinutes")
    Training trainingToEntity(TrainingDTO trainingDTO);

    List<Training> dtoListToEntityList(List<TrainingDTO> trainingDTOs);

    List<TrainingDTO> entityListToDTOList(List<Training> trainings);
}

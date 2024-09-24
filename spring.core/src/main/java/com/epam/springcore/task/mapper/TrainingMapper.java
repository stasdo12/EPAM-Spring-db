package com.epam.springcore.task.mapper;

import com.epam.springcore.task.dto.TrainingDTO;
import com.epam.springcore.task.model.Training;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TrainingMapper {

    TrainingDTO trainingToDTO(Training training);

    @Mapping(target = "trainingId", ignore = true)
    Training trainingToEntity(TrainingDTO trainingDTO);

    List<Training> dtoListToEntityList(List<TrainingDTO> trainingDTOs);

    List<TrainingDTO> entityListToDTOList(List<Training> trainings);
}

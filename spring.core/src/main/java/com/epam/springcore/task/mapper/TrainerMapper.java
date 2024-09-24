package com.epam.springcore.task.mapper;

import com.epam.springcore.task.dto.TrainerDTO;
import com.epam.springcore.task.model.Trainer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TrainerMapper {

    TrainerDTO trainerToDTO(Trainer trainer);

    @Mapping(target = "trainerId", ignore = true)
    Trainer trainerToEntity(TrainerDTO trainerDTO);

    List<Trainer> dtoListToEntityList(List<TrainerDTO> trainerDTOs);

    List<TrainerDTO> entityListToDTOList(List<Trainer> trainers);

    default Set<Trainer> dtoListToEntitySet(List<TrainerDTO> trainerDTOs) {
        if (trainerDTOs == null) {
            return null;
        }
        return trainerDTOs.stream()
                .map(this::trainerToEntity)
                .collect(Collectors.toSet());
    }

    default List<TrainerDTO> entitySetToDTOList(Set<Trainer> trainers) {
        if (trainers == null) {
            return null;
        }
        return trainers.stream()
                .map(this::trainerToDTO)
                .collect(Collectors.toList());
    }

}

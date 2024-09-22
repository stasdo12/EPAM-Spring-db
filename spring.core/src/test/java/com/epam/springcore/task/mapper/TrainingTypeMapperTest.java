package com.epam.springcore.task.mapper;

import com.epam.springcore.task.dto.TrainingTypeDTO;
import com.epam.springcore.task.model.TrainingType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TrainingTypeMapperTest {

    private final TrainingTypeMapper mapper = TrainingTypeMapper.INSTANCE;

    @Test
    void trainingTypeToDTO() {
        TrainingType trainingType = new TrainingType();
        trainingType.setId(1L);
        trainingType.setName("Cardio");

        TrainingTypeDTO result = mapper.trainingTypeToDTO(trainingType);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(trainingType.getName());
    }

    @Test
    void trainingTypeToEntity() {
        TrainingTypeDTO trainingTypeDTO = new TrainingTypeDTO();
        trainingTypeDTO.setName("Strength");

        TrainingType result = mapper.trainingTypeToEntity(trainingTypeDTO);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(trainingTypeDTO.getName());
        assertThat(result.getId()).isNull();
    }
}
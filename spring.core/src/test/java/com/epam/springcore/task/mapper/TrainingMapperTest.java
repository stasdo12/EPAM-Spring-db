package com.epam.springcore.task.mapper;

import com.epam.springcore.task.dto.TrainingDTO;
import com.epam.springcore.task.model.Training;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import static org.assertj.core.api.Assertions.assertThat;

class TrainingMapperTest {

    @Mock
    private TraineeMapper traineeMapper;

    @Mock
    private TrainerMapper trainerMapper;

    @InjectMocks
    private TrainingMapperImpl mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void trainingToDTO() {
        Training training = new Training();
        training.setTrainingId(1L);
        training.setTrainingName("Yoga");
        training.setDate(LocalDate.now());
        training.setDurationMinutes(60);

        TrainingDTO result = mapper.trainingToDTO(training);

        assertThat(result).isNotNull();
        assertThat(result.getTrainingName()).isEqualTo(training.getTrainingName());
        assertThat(result.getDate()).isEqualTo(training.getDate());
        assertThat(result.getDurationMinutes()).isEqualTo(training.getDurationMinutes());
    }

    @Test
    void trainingToEntity() {
        TrainingDTO trainingDTO = new TrainingDTO();
        trainingDTO.setTrainingName("Pilates");
        trainingDTO.setDate(LocalDate.now());
        trainingDTO.setDurationMinutes(45);

        Training result = mapper.trainingToEntity(trainingDTO);

        assertThat(result).isNotNull();
        assertThat(result.getTrainingName()).isEqualTo(trainingDTO.getTrainingName());
        assertThat(result.getDate()).isEqualTo(trainingDTO.getDate());
        assertThat(result.getDurationMinutes()).isEqualTo(trainingDTO.getDurationMinutes());
    }

}
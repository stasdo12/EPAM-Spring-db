package com.epam.springcore.task.service.impl;

import com.epam.springcore.task.mapper.TrainingMapper;
import com.epam.springcore.task.repo.TrainingRepository;
import com.epam.springcore.task.dto.TraineeDTO;
import com.epam.springcore.task.dto.TrainerDTO;
import com.epam.springcore.task.dto.TrainingDTO;
import com.epam.springcore.task.dto.TrainingTypeDTO;
import com.epam.springcore.task.model.Trainee;
import com.epam.springcore.task.model.Trainer;
import com.epam.springcore.task.model.Training;
import com.epam.springcore.task.model.TrainingType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class TrainingServiceTest {

    @Mock
    private TrainingRepository trainingRepository;
    @Mock
    private TrainingMapper trainingMapper;

    @InjectMocks
    private TrainingService trainingService;
    private TrainingDTO validTrainingDTO;
    private Training training;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        validTrainingDTO = new TrainingDTO();
        validTrainingDTO.setTrainee(new TraineeDTO());
        validTrainingDTO.setTrainer(new TrainerDTO());
        validTrainingDTO.setTrainingName("Sample training");
        validTrainingDTO.setTrainingType(new TrainingTypeDTO());
        validTrainingDTO.setDate(LocalDate.now());
        validTrainingDTO.setDurationMinutes(60);

        training = new Training();
        training.setTrainingId(1L);
        training.setTrainee(new Trainee());
        training.setTrainer(new Trainer());
        training.setTrainingName("Sample training");
        training.setTrainingType(new TrainingType());
        training.setDate(LocalDate.now());
        training.setDurationMinutes(60);
    }

    @Test
    void addTraining_ShouldSaveTraining_WhenValidData() {

        when(trainingMapper.trainingToEntity(any(TrainingDTO.class))).thenReturn(training);
        when(trainingMapper.trainingToDTO(any(Training.class))).thenReturn(validTrainingDTO);
        when(trainingRepository.save(any(Training.class))).thenReturn(training);

        TrainingDTO result = trainingService.addTraining(validTrainingDTO);
        assertNotNull(result);

        assertEquals(validTrainingDTO.getTrainingName(), result.getTrainingName());
        assertEquals(validTrainingDTO.getDurationMinutes(), result.getDurationMinutes());
    }

    @Test
    void addTraining_ShouldThrowException_WhenTrainingDTOIsNull() {
        assertThrows(IllegalArgumentException.class, () -> trainingService.addTraining(null));
    }

    @Test
    void addTraining_ShouldThrowException_WhenTraineeIsNull() {
        validTrainingDTO.setTrainee(null);
        assertThrows(IllegalArgumentException.class, () -> trainingService.addTraining(validTrainingDTO));
    }
}
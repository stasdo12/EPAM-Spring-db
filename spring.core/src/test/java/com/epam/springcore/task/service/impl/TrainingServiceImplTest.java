package com.epam.springcore.task.service.impl;

import com.epam.springcore.task.dao.TrainingDAO;
import com.epam.springcore.task.model.Training;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TrainingServiceImplTest {

    @Mock
    private TrainingDAO trainingDAO;

    @InjectMocks
    private TrainingServiceImpl trainingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_ShouldCreateTraining() {
        Training training = new Training();
        when(trainingDAO.create(training)).thenReturn(Optional.of(training));

        Optional<Training> result = trainingService.create(training);

        assertTrue(result.isPresent());
        assertEquals(training, result.get());
    }

    @Test
    void findById_ShouldReturnTraining() {
        long trainingId = 1L;
        Training training = new Training();
        when(trainingDAO.getById(trainingId)).thenReturn(Optional.of(training));

        Optional<Training> result = trainingService.findById(trainingId);

        assertTrue(result.isPresent());
        assertEquals(training, result.get());
    }

    @Test
    void getAllTrainings() {
        Training training = new Training();
        when(trainingDAO.getAllTrainings()).thenReturn(Collections.singletonList(training));

        List<Training> result = trainingService.getAllTrainings();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(training, result.get(0));
    }

}
package com.epam.springcore.task.dao.impl;

import com.epam.springcore.task.model.Training;
import com.epam.springcore.task.model.TrainingType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TrainingDAOImplTest {

    private TrainingDAOImpl trainingDAO;


    @BeforeEach
    public void setUp() {
        Map<Long, Training> trainingsStorage = new HashMap<>();
        trainingDAO = new TrainingDAOImpl(trainingsStorage);
        TrainingType bodybuilding = new TrainingType(1L, "BODYBUILDING");
        TrainingType pilates = new TrainingType(2L,"PILATES");

        Training training1 = new Training();
        training1.setTrainingId(1L);
        training1.setTraineeId(101L);
        training1.setTrainerId(201L);
        training1.setTrainingType(bodybuilding);
        training1.setDate(LocalDate.of(2024, 9, 1));
        training1.setDurationMinutes(60);
        trainingDAO.create(training1);

        Training training2 = new Training();
        training2.setTrainingId(2L);
        training2.setTraineeId(102L);
        training2.setTrainerId(202L);
        training2.setTrainingType(pilates);
        training2.setDate(LocalDate.of(2024, 9, 2));
        training2.setDurationMinutes(45);
        trainingDAO.create(training2);
    }

    @Test
    void create_shouldCreateTrainer() {
        TrainingType pilates = new TrainingType(2L,"PILATES");
        Training newTraining = new Training();
        newTraining.setTrainingId(3L);
        newTraining.setTraineeId(103L);
        newTraining.setTrainerId(203L);
        newTraining.setTrainingType(pilates);
        newTraining.setDate(LocalDate.of(2024, 9, 3));
        newTraining.setDurationMinutes(30);

        Optional<Training> result = trainingDAO.create(newTraining);

        assertTrue(result.isPresent());
        assertEquals(newTraining, result.get());
    }

    @Test
    void getById_ShouldReturnTraining() {
        Optional<Training> result = trainingDAO.getById(1L);

        assertTrue(result.isPresent());
        assertThat(result.get().getTrainingId()).isEqualTo(1L);
    }

    @Test
    void getAllTrainings_ShouldReturnListOfTrainings(){
        List<Training> result = trainingDAO.getAllTrainings();
        assertThat(result).hasSize(2);
    }

}
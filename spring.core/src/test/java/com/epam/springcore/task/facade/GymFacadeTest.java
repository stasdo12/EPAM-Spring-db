package com.epam.springcore.task.facade;

import com.epam.springcore.task.model.Trainee;
import com.epam.springcore.task.model.Trainer;
import com.epam.springcore.task.model.Training;
import com.epam.springcore.task.service.TraineeService;
import com.epam.springcore.task.service.TrainerService;
import com.epam.springcore.task.service.TrainingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GymFacadeTest {

    @Mock
    private TraineeService traineeService;

    @Mock
    private TrainerService trainerService;

    @Mock
    private TrainingService trainingService;

    @InjectMocks
    private GymFacade gymFacade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getTraineeById_ShouldDelegateToTraineeService() {
        Trainee trainee = new Trainee();
        trainee.setTraineeId(1L);

        when(traineeService.getById(1L)).thenReturn(Optional.of(trainee));

        Optional<Trainee> result = gymFacade.traineeService().getById(1L);

        verify(traineeService).getById(1L);
        assertTrue(result.isPresent());
        assertEquals(trainee, result.get());
    }


    @Test
    void getTrainerById_ShouldDelegateToTrainerService() {
        Trainer trainer = new Trainer();
        trainer.setTrainerId(1L);

        when(trainerService.getById(1L)).thenReturn(Optional.of(trainer));

        Optional<Trainer> result = gymFacade.trainerService().getById(1L);

        verify(trainerService).getById(1L);
        assertTrue(result.isPresent());
        assertEquals(trainer, result.get());
    }

    @Test
    void getTrainingById_ShouldDelegateToTrainingService() {
        Training training = new Training();
        training.setTrainingId(1L);

        when(trainingService.findById(1L)).thenReturn(Optional.of(training));

        Optional<Training> result = gymFacade.trainingService().findById(1L);

        verify(trainingService).findById(1L);
        assertTrue(result.isPresent());
        assertEquals(training, result.get());
    }
    @Test
    void createTrainee_ShouldDelegateToTraineeService() {
        Trainee trainee = new Trainee();
        trainee.setTraineeId(1L);

        when(traineeService.create(any(Trainee.class))).thenReturn(Optional.of(trainee));


        Optional<Trainee> result = gymFacade.traineeService().create(trainee);


        verify(traineeService).create(trainee);
        assertTrue(result.isPresent());
        assertEquals(trainee, result.get());
    }


    @Test
    void createTrainer_ShouldDelegateToTrainerService(){
        Trainer trainer = new Trainer();
        trainer.setTrainerId(1L);

        when(trainerService.create(any(Trainer.class))).thenReturn(Optional.of(trainer));

        Optional<Trainer> result = gymFacade.trainerService().create(trainer);

        verify(trainerService).create(trainer);
        assertTrue(result.isPresent());
        assertEquals(trainer, result.get());

    }

    @Test
    void createTraining_ShouldDelegateToTrainingService(){
        Training training = new Training();
        training.setTrainerId(1L);

        when(trainingService.create(any(Training.class))).thenReturn(Optional.of(training));

        Optional<Training> result = gymFacade.trainingService().create(training);

        verify(trainingService).create(training);
        assertTrue(result.isPresent());
        assertEquals(training, result.get());




    }

}
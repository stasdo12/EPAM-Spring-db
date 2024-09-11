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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void testCreateTrainee() {
        Trainee trainee = new Trainee();
        when(traineeService.create(trainee)).thenReturn(Optional.of(trainee));

        Optional<Trainee> result = gymFacade.createTrainee(trainee);

        assertEquals(Optional.of(trainee), result);

    }


    @Test
    void testUpdateTrainee() {
        Trainee trainee = new Trainee();
        when(traineeService.update(trainee)).thenReturn(Optional.of(trainee));

        Optional<Trainee> result = gymFacade.updateTrainee(trainee);

        assertEquals(Optional.of(trainee), result);

    }

    @Test
    void testGetAllTrainees() {
        List<Trainee> trainees = List.of(new Trainee(), new Trainee());
        when(traineeService.getTrainees()).thenReturn(trainees);

        List<Trainee> result = gymFacade.getAllTrainees();

        assertEquals(trainees, result);

    }

    @Test
    void testGetTraineeById() {
        Trainee trainee = new Trainee();
        long traineeId = 1L;
        when(traineeService.getById(traineeId)).thenReturn(Optional.of(trainee));

        Optional<Trainee> result = gymFacade.getTraineeById(traineeId);

        assertEquals(Optional.of(trainee), result);

    }


    @Test
    void testCreateTrainer() {
        Trainer trainer = new Trainer();
        when(trainerService.create(trainer)).thenReturn(Optional.of(trainer));

        Optional<Trainer> result = gymFacade.createTrainer(trainer);

        assertEquals(Optional.of(trainer), result);

    }

    @Test
    void testGetAllTrainers() {
        List<Trainer> trainers = List.of(new Trainer(), new Trainer());
        when(trainerService.getAllTrainers()).thenReturn(trainers);

        List<Trainer> result = gymFacade.getAllTrainers();

        assertEquals(trainers, result);

    }

    @Test
    void testGetTrainerById() {
        Trainer trainer = new Trainer();
        long trainerId = 1L;
        when(trainerService.getById(trainerId)).thenReturn(Optional.of(trainer));

        Optional<Trainer> result = gymFacade.getTrainerById(trainerId);

        assertEquals(Optional.of(trainer), result);
    }

    @Test
    void testCreateTraining() {
        Training training = new Training();
        when(trainingService.create(training)).thenReturn(Optional.of(training));

        Optional<Training> result = gymFacade.createTraining(training);

        assertEquals(Optional.of(training), result);
    }

    @Test
    void testGetAllTrainings() {
        List<Training> trainings = List.of(new Training(), new Training());
        when(trainingService.getAllTrainings()).thenReturn(trainings);

        List<Training> result = gymFacade.getAllTrainings();

        assertEquals(trainings, result);
    }

    @Test
    void testGetTrainingById() {
        Training training = new Training();
        long trainingId = 1L;
        when(trainingService.findById(trainingId)).thenReturn(Optional.of(training));

        Optional<Training> result = gymFacade.getTrainingById(trainingId);

        assertEquals(Optional.of(training), result);
    }

}
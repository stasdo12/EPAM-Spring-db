package com.epam.springcore.task.facade;

import com.epam.springcore.task.dto.PassUsernameDTO;
import com.epam.springcore.task.dto.TraineeDTO;
import com.epam.springcore.task.dto.TrainerDTO;
import com.epam.springcore.task.dto.TrainingDTO;
import com.epam.springcore.task.model.Trainee;
import com.epam.springcore.task.service.impl.TraineeService;
import com.epam.springcore.task.service.impl.TrainerService;
import com.epam.springcore.task.service.impl.TrainingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
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
    void testSaveTrainer() {
        TrainerDTO trainerDTO = new TrainerDTO();
        PassUsernameDTO passUsernameDTO = new PassUsernameDTO();
        when(trainerService.saveTrainer(trainerDTO)).thenReturn(passUsernameDTO);

        PassUsernameDTO result = gymFacade.saveTrainer(trainerDTO);

        assertEquals(passUsernameDTO, result);
        verify(trainerService).saveTrainer(trainerDTO);
    }

    @Test
    void testFindTrainerById() {
        Long trainerId = 1L;
        TrainerDTO trainerDTO = new TrainerDTO();
        when(trainerService.findById(trainerId)).thenReturn(Optional.of(trainerDTO));

        Optional<TrainerDTO> result = gymFacade.findTrainerById(trainerId);

        assertTrue(result.isPresent());
        assertEquals(trainerDTO, result.get());
    }

    @Test
    void testChangeTrainerPassword() {
        PassUsernameDTO passUsernameDTO = new PassUsernameDTO();
        when(trainerService.changeTrainerPassword(passUsernameDTO)).thenReturn(passUsernameDTO);

        PassUsernameDTO result = gymFacade.changeTrainerPassword(passUsernameDTO);

        assertEquals(passUsernameDTO, result);
        verify(trainerService).changeTrainerPassword(passUsernameDTO);
    }

    @Test
    void testUpdateTrainerProfile() {
        String username = "trainerUsername";
        TrainerDTO updatedTrainerDTO = new TrainerDTO();
        when(trainerService.updateTrainerProfile(username, updatedTrainerDTO)).thenReturn(updatedTrainerDTO);

        TrainerDTO result = gymFacade.updateTrainerProfile(username, updatedTrainerDTO);

        assertEquals(updatedTrainerDTO, result);
        verify(trainerService).updateTrainerProfile(username, updatedTrainerDTO);
    }

    @Test
    void activateDeactivateTrainer() {
        String username = "trainerUsername";
        boolean isActive = true;

        gymFacade.activateDeactivateTrainer(username, isActive);

        verify(trainerService).activateDeactivateTrainer(username, isActive);
    }

    @Test
    void matchTrainerCredentialsTrainee() {
        PassUsernameDTO passUsernameDTO = new PassUsernameDTO();
        boolean expectedMatch = true;

        when(trainerService.matchTrainerCredentials(passUsernameDTO)).thenReturn(expectedMatch);

        boolean result = gymFacade.matchTrainerCredentialsTrainee(passUsernameDTO);

        assertTrue(result);
        verify(trainerService).matchTrainerCredentials(passUsernameDTO);
    }

    @Test
    void findTrainerByUsername() {
        String username = "trainerUsername";
        TrainerDTO trainerDTO = new TrainerDTO();

        when(trainerService.findByUsername(username)).thenReturn(Optional.of(trainerDTO));

        Optional<TrainerDTO> result = gymFacade.findTrainerByUsername(username);

        assertTrue(result.isPresent());
        assertEquals(trainerDTO, result.get());
        verify(trainerService).findByUsername(username);
    }

    @Test
    void testGetTrainerTrainingsByCriteria() {
        String trainerUsername = "trainer";
        LocalDate fromDate = LocalDate.now();
        LocalDate toDate = LocalDate.now();
        String traineeUsername = "trainee";
        String trainingName = "training";
        List<TrainingDTO> trainingDTOs = Collections.emptyList();
        when(trainerService.getTrainerTrainingsByCriteria(trainerUsername, fromDate, toDate, traineeUsername, trainingName))
                .thenReturn(trainingDTOs);

        List<TrainingDTO> result = gymFacade.getTrainerTrainingsByCriteria(trainerUsername, fromDate, toDate, traineeUsername, trainingName);

        assertEquals(trainingDTOs, result);
        verify(trainerService).getTrainerTrainingsByCriteria(trainerUsername, fromDate, toDate, traineeUsername, trainingName);
    }

    @Test
    void getTrainersNotAssignedToTrainee() {
        String traineeUsername = "traineeUsername";
        List<TrainerDTO> trainersList = List.of(new TrainerDTO(), new TrainerDTO());

        when(trainerService.getTrainersNotAssignedToTrainee(traineeUsername)).thenReturn(trainersList);

        List<TrainerDTO> result = gymFacade.getTrainersNotAssignedToTrainee(traineeUsername);

        assertEquals(trainersList, result);
        verify(trainerService).getTrainersNotAssignedToTrainee(traineeUsername);
    }

    @Test
    void saveTrainee() {
        TraineeDTO traineeDTO = new TraineeDTO();
        PassUsernameDTO passUsernameDTO = new PassUsernameDTO();
        when(traineeService.saveTrainee(traineeDTO)).thenReturn(passUsernameDTO);

        PassUsernameDTO result = gymFacade.saveTrainee(traineeDTO);

        assertEquals(passUsernameDTO, result);
        verify(traineeService).saveTrainee(traineeDTO);
    }

    @Test
    void findTraineeById() {
        long traineeId = 1L;
        TraineeDTO traineeDTO = new TraineeDTO();
        when(traineeService.findById(traineeId)).thenReturn(Optional.of(traineeDTO));

        Optional<TraineeDTO> result = gymFacade.findTraineeById(traineeId);

        assertTrue(result.isPresent());
        assertEquals(traineeDTO, result.get());
        verify(traineeService).findById(traineeId);
    }

    @Test
    void findTraineeByUserId() {
        Long userId = 1L;
        TraineeDTO traineeDTO = new TraineeDTO();
        when(traineeService.findByUserId(userId)).thenReturn(Optional.of(traineeDTO));

        Optional<TraineeDTO> result = gymFacade.findTraineeByUserId(userId);

        assertTrue(result.isPresent());
        assertEquals(traineeDTO, result.get());
        verify(traineeService).findByUserId(userId);
    }

    @Test
    void findTraineeByUsername() {
        String username = "traineeUsername";
        TraineeDTO traineeDTO = new TraineeDTO();
        when(traineeService.findByUsername(username)).thenReturn(Optional.of(traineeDTO));

        Optional<TraineeDTO> result = gymFacade.findTraineeByUsername(username);

        assertTrue(result.isPresent());
        assertEquals(traineeDTO, result.get());
        verify(traineeService).findByUsername(username);
    }

    @Test
    void changeTraineePassword() {
        PassUsernameDTO passUsernameDTO = new PassUsernameDTO();
        when(traineeService.changeTraineePassword(passUsernameDTO)).thenReturn(passUsernameDTO);

        PassUsernameDTO result = gymFacade.changeTraineePassword(passUsernameDTO);

        assertEquals(passUsernameDTO, result);
        verify(traineeService).changeTraineePassword(passUsernameDTO);
    }

    @Test
    void updateTraineeProfile() {
        String username = "traineeUsername";
        TraineeDTO updatedTraineeDTO = new TraineeDTO();
        when(traineeService.updateTraineeProfile(username, updatedTraineeDTO)).thenReturn(updatedTraineeDTO);

        TraineeDTO result = gymFacade.updateTraineeProfile(username, updatedTraineeDTO);

        assertEquals(updatedTraineeDTO, result);
        verify(traineeService).updateTraineeProfile(username, updatedTraineeDTO);
    }

    @Test
    void activateDeactivateProfile() {
        String username = "traineeUsername";
        boolean isActive = true;

        gymFacade.activateDeactivateProfile(username, isActive);

        verify(traineeService).activateDeactivateTrainee(username, isActive);
    }

    @Test
    void deleteTrainee() {
        String username = "traineeUsername";

        gymFacade.deleteTrainee(username);

        verify(traineeService).deleteTrainee(username);
    }

    @Test
    void matchTraineeCredentials() {
        PassUsernameDTO passUsernameDTO = new PassUsernameDTO();
        boolean expectedMatch = true;
        when(traineeService.matchTraineeCredentials(passUsernameDTO)).thenReturn(expectedMatch);

        boolean result = gymFacade.matchTraineeCredentials(passUsernameDTO);

        assertTrue(result);
        verify(traineeService).matchTraineeCredentials(passUsernameDTO);
    }

    @Test
    void getTraineeTrainingsByCriteria() {
        String traineeUsername = "traineeUsername";
        LocalDate fromDate = LocalDate.now();
        LocalDate toDate = LocalDate.now();
        String trainerUsername = "trainerUsername";
        String trainingName = "training";
        List<TrainingDTO> trainingDTOs = List.of(new TrainingDTO());
        when(traineeService.getTraineeTrainingsByCriteria(traineeUsername, fromDate, toDate, trainerUsername, trainingName))
                .thenReturn(trainingDTOs);

        List<TrainingDTO> result = gymFacade.getTraineeTrainingsByCriteria(traineeUsername, fromDate, toDate, trainerUsername, trainingName);

        assertEquals(trainingDTOs, result);
        verify(traineeService).getTraineeTrainingsByCriteria(traineeUsername, fromDate, toDate, trainerUsername, trainingName);
    }

    @Test
    void updateTraineeTrainers() {
        String traineeUsername = "traineeUsername";
        Set<TrainerDTO> newTrainerDTOs = Set.of(new TrainerDTO());
        Trainee updatedTrainee = new Trainee();
        when(traineeService.updateTraineeTrainers(traineeUsername, newTrainerDTOs)).thenReturn(updatedTrainee);

        Trainee result = gymFacade.updateTraineeTrainers(traineeUsername, newTrainerDTOs);

        assertEquals(updatedTrainee, result);
        verify(traineeService).updateTraineeTrainers(traineeUsername, newTrainerDTOs);
    }

    @Test
    void addTraining() {
        TrainingDTO trainingDTO = new TrainingDTO();
        when(trainingService.addTraining(trainingDTO)).thenReturn(trainingDTO);

        TrainingDTO result = gymFacade.addTraining(trainingDTO);

        assertEquals(trainingDTO, result);
        verify(trainingService).addTraining(trainingDTO);
    }
}
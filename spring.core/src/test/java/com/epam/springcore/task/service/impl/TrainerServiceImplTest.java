package com.epam.springcore.task.service.impl;

import com.epam.springcore.task.dao.TraineeDAO;
import com.epam.springcore.task.dao.TrainerDAO;
import com.epam.springcore.task.model.Trainer;
import com.epam.springcore.task.model.User;
import com.epam.springcore.task.utils.NameGenerator;
import com.epam.springcore.task.utils.PasswordGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class TrainerServiceImplTest {
    @Mock
    private TrainerDAO trainerDAO;

    @Mock
    private TraineeDAO traineeDAO;

    @Mock
    private PasswordGenerator passwordGenerator;

    @Mock
    private NameGenerator nameGenerator;

    @InjectMocks
    private TrainerServiceImpl trainerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_ShouldCreateTrainerWhenUserIsNotNull() {

        Trainer trainer = new Trainer();
        User user = new User();
        trainer.setUser(user);

        when(trainerDAO.getMaxId()).thenReturn(1L);
        when(trainerDAO.create(1L, trainer)).thenReturn(Optional.of(trainer));
        when(nameGenerator.generateUsername(user, Collections.emptyList(), Collections.emptyList())).thenReturn("username");
        when(passwordGenerator.generatePassword()).thenReturn("password");

        Optional<Trainer> result = trainerService.create(trainer);

        assertTrue(result.isPresent());
        assertEquals(trainer, result.get());
        assertEquals("username", user.getUserName());
        assertEquals("password", user.getPassword());

    }

    @Test
    void create_ShouldThrowExceptionWhenUserIsNull() {

        Trainer trainer = new Trainer();

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> trainerService.create(trainer));
        assertEquals("User must not be null in Trainer", thrown.getMessage());
    }

    @Test
    void update_ShouldUpdateTrainer() {

        Trainer trainer = new Trainer();
        when(trainerDAO.update(trainer)).thenReturn(Optional.of(trainer));

        Optional<Trainer> result = trainerService.update(trainer);

        assertTrue(result.isPresent());
        assertEquals(trainer, result.get());
    }

    @Test
    void getById_ShouldReturnTrainer() {

        long trainerId = 1L;
        Trainer trainer = new Trainer();
        when(trainerDAO.getById(trainerId)).thenReturn(Optional.of(trainer));

        Optional<Trainer> result = trainerService.getById(trainerId);

        assertTrue(result.isPresent());
        assertEquals(trainer, result.get());
    }

    @Test
    void getByUsername() {

        String username = "username";
        Trainer trainer = new Trainer();
        when(trainerDAO.getByUsername(username)).thenReturn(Optional.of(trainer));

        Optional<Trainer> result = trainerService.getByUsername(username);

        assertTrue(result.isPresent());
        assertEquals(trainer, result.get());
    }

    @Test
    void getAllTrainers_ShouldReturnListOfTrainers() {

        Trainer trainer = new Trainer();
        when(trainerDAO.getAllTrainers()).thenReturn(Collections.singletonList(trainer));

        List<Trainer> result = trainerService.getAllTrainers();

        assertFalse(result.isEmpty());
        assertEquals(trainer, result.get(0));
    }

}
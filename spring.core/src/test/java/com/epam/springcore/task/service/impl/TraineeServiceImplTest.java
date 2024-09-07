package com.epam.springcore.task.service.impl;

import com.epam.springcore.task.dao.TraineeDAO;
import com.epam.springcore.task.dao.TrainerDAO;
import com.epam.springcore.task.model.Trainee;
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

class TraineeServiceImplTest {

    @Mock
    private TraineeDAO traineeDAO;

    @Mock
    private TrainerDAO trainerDAO;

    @Mock
    private PasswordGenerator passwordGenerator;

    @Mock
    private NameGenerator nameGenerator;

    @InjectMocks
    private TraineeServiceImpl traineeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_ShouldCreateTraineeWhenUserIsNotNull() {
        Trainee trainee = new Trainee();
        User user = new User();
        trainee.setUser(user);


        when(traineeDAO.create(trainee)).thenReturn(Optional.of(trainee));
        when(nameGenerator.generateUsername(user, Collections.emptyList(),
                Collections.emptyList())).thenReturn("username");
        when(passwordGenerator.generatePassword()).thenReturn("password");

        Optional<Trainee> result = traineeService.create(trainee);

        assertTrue(result.isPresent());
        assertEquals("username", user.getUserName());
        assertEquals("password", user.getPassword());
    }

    @Test
    void create_ShouldThrowExceptionWhenUserIsNull() {

        Trainee trainee = new Trainee();

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> traineeService.create(trainee));
        assertEquals("User must not be null in Trainee", thrown.getMessage());
    }

    @Test
    void getById_ShouldReturnTrainee() {

        long traineeId = 1L;
        Trainee trainee = new Trainee();

        when(traineeDAO.findById(traineeId)).thenReturn(Optional.of(trainee));

        Optional<Trainee> result = traineeService.getById(traineeId);

        assertTrue(result.isPresent());
        assertEquals(trainee, result.get());
    }

    @Test
    void update_ShouldUpdateTrainee() {

        Trainee trainee = new Trainee();
        when(traineeDAO.update(trainee)).thenReturn(Optional.of(trainee));

        Optional<Trainee> result = traineeService.update(trainee);

        assertTrue(result.isPresent());
        assertEquals(trainee, result.get());

    }

    @Test
    void delete_ShouldReturnTrueWhenDeleted() {

        long traineeId = 1L;
        when(traineeDAO.deleteById(traineeId)).thenReturn(true);

        boolean result = traineeService.delete(traineeId);

        assertTrue(result);

    }

    @Test
    void getByUsername_ShouldReturnTrainee() {

        String username = "username";
        Trainee trainee = new Trainee();

        when(traineeDAO.findByUsername(username)).thenReturn(Optional.of(trainee));

        Optional<Trainee> result = traineeService.getByUsername(username);

        assertTrue(result.isPresent());
        assertEquals(trainee, result.get());
    }

    @Test
    void getTrainees_ShouldReturnListTrainee() {

        Trainee trainee = new Trainee();
        when(traineeDAO.getAllTrainees()).thenReturn(Collections.singletonList(trainee));

        List<Trainee> result = traineeService.getTrainees();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(trainee, result.get(0));
    }

}
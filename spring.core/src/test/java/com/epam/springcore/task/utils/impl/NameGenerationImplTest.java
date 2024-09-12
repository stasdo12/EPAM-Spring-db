package com.epam.springcore.task.utils.impl;

import com.epam.springcore.task.model.Trainee;
import com.epam.springcore.task.model.Trainer;
import com.epam.springcore.task.model.User;
import com.epam.springcore.task.utils.NameGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NameGenerationImplTest {

    private NameGenerator nameGenerator;

    @BeforeEach
    public void setUp() {
        nameGenerator = new NameGenerationImpl();
    }

    @Test
    public void testGenerateUsernameWithoutDuplicates() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");

        String username = nameGenerator.generateUsername(user);
        assertEquals("John.Doe", username);
    }

    @Test
    public void testGenerateUsernameWithDuplicates() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");

        User existingUser1 = new User();
        existingUser1.setUsername("John.Doe1");

        User existingUser2 = new User();
        existingUser2.setUsername("John.Doe2");

        Trainee trainee1 = Mockito.mock(Trainee.class);
        Mockito.when(trainee1.getUser()).thenReturn(existingUser1);

        Trainer trainer1 = Mockito.mock(Trainer.class);
        Mockito.when(trainer1.getUser()).thenReturn(existingUser2);

        List<Trainee> trainees = List.of(trainee1);
        List<Trainer> trainers = List.of(trainer1);

        String newUsername = nameGenerator.generateUsername(user, trainees, trainers);
        assertEquals("John.Doe3", newUsername);
    }
}
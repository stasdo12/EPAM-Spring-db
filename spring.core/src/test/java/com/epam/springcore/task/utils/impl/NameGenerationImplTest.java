package com.epam.springcore.task.utils.impl;

import com.epam.springcore.task.model.User;
import com.epam.springcore.task.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class NameGenerationImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private NameGenerationImpl nameGenerator;

    @BeforeEach
    public void setUp() {
        nameGenerator = new NameGenerationImpl(userRepository);
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

        List<String> takenUsernames = List.of("John.Doe1", "John.Doe2");
        Mockito.when(userRepository.findByUsernameStartingWith("John.Doe"))
                .thenReturn(takenUsernames);

        String newUsername = nameGenerator.generateUniqueUsername(user);

        assertEquals("John.Doe3", newUsername);
    }
}
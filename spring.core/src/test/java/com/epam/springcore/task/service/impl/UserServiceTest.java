package com.epam.springcore.task.service.impl;

import com.epam.springcore.task.dto.PassUsernameDTO;
import com.epam.springcore.task.model.User;
import com.epam.springcore.task.repo.UserRepository;
import com.epam.springcore.task.utils.NameGenerator;
import com.epam.springcore.task.utils.PasswordGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private NameGenerator nameGenerator;
    @Mock
    private PasswordGenerator passwordGenerator;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGenerateAndSaveUser() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");

        when(nameGenerator.generateUniqueUsername(user)).thenReturn("john.doe");
        when(passwordGenerator.generatePassword()).thenReturn("password123");
        when(passwordEncoder.encode("password123")).thenReturn("hashedPassword");

        PassUsernameDTO result = userService.generateAndSaveUser(user);

        assertEquals("john.doe", result.getUsername());
        assertEquals("password123", result.getPassword());

        verify(userRepository, times(1)).save(user);
        assertEquals("hashedPassword", user.getPassword());
        assertTrue(user.isActive());
    }

    @Test
    void testMatchUserCredentials_WhenUserExistsAndPasswordMatches() {

        PassUsernameDTO passUsernameDTO = new PassUsernameDTO("john.doe", "password123");
        User user = new User();
        user.setUsername("john.doe");
        user.setPassword("hashedPassword");

        when(userRepository.findByUsername("john.doe")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password123", "hashedPassword")).thenReturn(true);

        boolean result = userService.matchUserCredentials(passUsernameDTO);

        assertTrue(result);
    }

    @Test
    void testMatchUserCredentials_WhenUserDoesNotExist() {

        PassUsernameDTO passUsernameDTO = new PassUsernameDTO("john.doe", "password123");
        when(userRepository.findByUsername("john.doe")).thenReturn(Optional.empty());

        boolean result = userService.matchUserCredentials(passUsernameDTO);

        assertFalse(result);
    }

    @Test
    void testChangeUserPassword() {
        String username = "john.doe";
        String newPassword = "newPassword123";
        User user = new User();
        user.setUsername(username);
        user.setPassword("oldPassword");

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(newPassword)).thenReturn("hashedNewPassword");

        userService.changeUserPassword(username, newPassword);

        verify(userRepository, times(1)).save(user);
        assertEquals("hashedNewPassword", user.getPassword());
    }

    @Test
    void testActivateDeactivateUser() {
        String username = "john.doe";
        User user = new User();
        user.setUsername(username);
        user.setActive(false);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        userService.activateDeactivateUser(username, true);

        verify(userRepository, times(1)).save(user);
        assertTrue(user.isActive());

    }
}
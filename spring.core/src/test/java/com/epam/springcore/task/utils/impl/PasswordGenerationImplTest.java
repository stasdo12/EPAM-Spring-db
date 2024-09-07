package com.epam.springcore.task.utils.impl;

import com.epam.springcore.task.utils.PasswordGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PasswordGenerationImplTest {


    private PasswordGenerator passwordGenerator;

    @BeforeEach
    void setUp() {
        passwordGenerator = new PasswordGenerationImpl();
    }

    @Test
    void generatePassword_ShouldGeneratePasswordOfCorrectLength() {
        String password = passwordGenerator.generatePassword();
        int passLength = 10;
        assertThat(password).hasSize(passLength);
    }

    @Test
    void generatePassword_ShouldContainUpperCaseLetters() {
        String password = passwordGenerator.generatePassword();
        assertThat(password).matches(".*[A-Z].*");
    }

    @Test
    void generatePassword_ShouldContainLowerCaseLetters() {
        String password = passwordGenerator.generatePassword();
        assertThat(password).matches(".*[a-z].*");
    }

    @Test
    void generatePassword_ShouldContainDigits() {
        String password = passwordGenerator.generatePassword();
        assertThat(password).matches(".*\\d.*");
    }

    @Test
    void generatePassword_ShouldBeRandom() {
        String password1 = passwordGenerator.generatePassword();
        String password2 = passwordGenerator.generatePassword();
        assertThat(password1).isNotEqualTo(password2);
    }
}
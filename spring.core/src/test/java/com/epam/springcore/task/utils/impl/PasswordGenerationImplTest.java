package com.epam.springcore.task.utils.impl;

import com.epam.springcore.task.utils.PasswordGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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
    void generatePasswordShouldGeneratePasswordOfCorrectLength() {
        String password = passwordGenerator.generatePassword();
        int passLength = 10;
        assertThat(password).hasSize(passLength);
    }

    @Test
    void generatePasswordShouldContainDigits() {
        String password = passwordGenerator.generatePassword();
        assertThat(password).matches(".*\\d.*");
    }

    @Test
    void generatePasswordShouldBeRandom() {
        String password1 = passwordGenerator.generatePassword();
        String password2 = passwordGenerator.generatePassword();
        assertThat(password1).isNotEqualTo(password2);
    }

    @ParameterizedTest
    @ValueSource(strings = {"upper", "lower"})
    void generatePasswordShouldContainUpperCaseAndLowerCaseLetters(String caseType) {
        String password = passwordGenerator.generatePassword();

        switch (caseType) {
            case "upper" -> assertThat(password).matches(".*[A-Z].*");
            case "lower" -> assertThat(password).matches(".*[a-z].*");
            default -> throw new IllegalArgumentException("Unexpected case type: " + caseType);
        }
    }
}
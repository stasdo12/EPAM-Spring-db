package com.epam.springcore.task.controller.impl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.epam.springcore.task.facade.GymFacade;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TrainingController.class)
class TrainingControllerTest {

    @MockBean
    private GymFacade gymFacade;

    @Autowired
    private MockMvc mockMvc;

    private String trainingJson;

    @BeforeEach
    void setUp() {
        trainingJson = "{ \"trainee\": { \"firstName\": \"Ivan\", \"lastName\": \"Ivanov\", \"username\": \"ivan123.secret\" },"
                + "\"trainer\": { \"firstName\": \"Stan\", \"lastName\": \"Stanislav\", \"username\": \"Stan.secret\" },"
                + "\"trainingName\": \"Yoga Session\","
                + "\"trainingType\": { \"id\": 1, \"name\": \"Yoga\" },"
                + "\"date\": \"2024-09-30\","
                + "\"durationMinutes\": 60 }";
    }

    @Test
    void testAddTraining_Success() throws Exception {
        mockMvc.perform(post("/api/trainings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(trainingJson))
                .andExpect(status().isOk());
    }
}
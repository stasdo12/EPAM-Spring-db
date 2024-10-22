package com.epam.springcore.task.security;

import com.epam.springcore.task.dto.TraineeDTO;
import com.epam.springcore.task.dto.TrainerDTO;
import com.epam.springcore.task.dto.TrainingTypeDTO;
import com.epam.springcore.task.dto.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {

    }

    @Test
    void shouldPermitAccessToLoginEndpoint() throws Exception {
        String jsonRequest = "{\"username\":\"stan\", \"password\":\"100\"}";

        mockMvc.perform(post("/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                        .with(csrf()))
                .andExpect(status().isOk());
         }

    @Test
    @WithMockUser
    void shouldPermitAccessToTraineeRegisterEndpoint() throws Exception {

        UserDTO userDTO = new UserDTO("John.Doe", "John", "Doe");
        TraineeDTO traineeDTO = new TraineeDTO();
        traineeDTO.setUser(userDTO);
        traineeDTO.setBirthday(LocalDate.of(2000, 1, 1));
        traineeDTO.setAddress("123 Main St");

        String traineeJson = objectMapper.writeValueAsString(traineeDTO);

        mockMvc.perform(post("/trainees/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(traineeJson)
                        .with(csrf()))
                .andExpect(status().isCreated());
    }


    @Test
    @WithMockUser
    void shouldPermitAccessToTrainerRegisterEndpoint() throws Exception {
        UserDTO userDTO = new UserDTO("Jane.Doe", "Jane", "Doe");
        TrainerDTO trainerDTO = new TrainerDTO();
        trainerDTO.setUser(userDTO);

        TrainingTypeDTO trainingTypeDTO = new TrainingTypeDTO("Yoga");
        trainerDTO.setSpecialization(trainingTypeDTO);

        String trainerJson = objectMapper.writeValueAsString(trainerDTO);

        mockMvc.perform(post("/trainers/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(trainerJson)
                        .with(csrf()))
                .andExpect(status().isCreated());
    }


    @Test
    void shouldDenyAccessToProtectedEndpointWithoutAuthentication() throws Exception {
        mockMvc.perform(post("/some/protected/endpoint")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "stan")
    void shouldAllowAccessToTraineeEndpointWithAuthentication() throws Exception {

        mockMvc.perform(get("/trainees/stan")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk());
    }
}
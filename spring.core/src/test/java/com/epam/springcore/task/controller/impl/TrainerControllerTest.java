package com.epam.springcore.task.controller.impl;

import com.epam.springcore.task.config.TestSecurityConfig;
import com.epam.springcore.task.dto.*;
import com.epam.springcore.task.facade.GymFacade;
import com.epam.springcore.task.filter.JwtRequestFilter;
import com.epam.springcore.task.model.User;
import com.epam.springcore.task.security.UserGymDetails;
import com.epam.springcore.task.service.impl.JwtService;
import com.epam.springcore.task.service.impl.UserDetailsServiceImpl;
import com.epam.springcore.task.utils.impl.JwtTokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TrainerController.class)
@Import({JwtTokenUtils.class, JwtRequestFilter.class, TestSecurityConfig.class})
class TrainerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GymFacade gymFacade;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private JwtService jwtTokenUtils;

    private TrainerDTO trainerDTO;
    private List<TrainingDTO> trainings;
    private LocalDate from;
    private LocalDate to;
    private String traineeUsername;
    private String trainingType;

    @BeforeEach
    void setUp() {
        trainerDTO = new TrainerDTO();
        UserDTO user = new UserDTO();
        user.setUsername("trainer1");
        user.setFirstName("Trainer");
        user.setLastName("One");
        trainerDTO.setUser(user);

        trainings = new ArrayList<>();
        from = LocalDate.of(2024, 1, 1);
        to = LocalDate.of(2024, 12, 31);
        traineeUsername = "john.doe";
        trainingType = "Yoga";

        TrainingDTO training1 = new TrainingDTO();
        training1.setTrainingName("Morning Yoga");
        training1.setDurationMinutes(60);
        training1.setDate(LocalDate.of(2024, 1, 10));
        trainings.add(training1);

        TrainingDTO training2 = new TrainingDTO();
        training2.setTrainingName("Evening Yoga");
        training2.setDurationMinutes(45);
        training2.setDate(LocalDate.of(2024, 1, 20));
        trainings.add(training2);
    }

    @Test
    void registerTrainerShouldReturnCreatedWhenTrainerIsSuccessfullyRegistered() throws Exception {
        String username = "trainerUser";
        String password = "trainerPassword";
        String token = "trainerToken";
        PassUsernameDTO passUsernameDTO = new PassUsernameDTO(username, password);
        TrainerDTO trainerDTO = new TrainerDTO();

        when(gymFacade.saveTrainer(any(TrainerDTO.class))).thenReturn(passUsernameDTO);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(new UserGymDetails(new User()));
        when(jwtTokenUtils.generateToken(any(UserDetails.class))).thenReturn(token);

        mockMvc.perform(post("/trainers/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(trainerDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token").value(token));

        verify(gymFacade, times(1)).saveTrainer(any(TrainerDTO.class));
        verify(userDetailsService, times(1)).loadUserByUsername(username);
        verify(jwtTokenUtils, times(1)).generateToken(any(UserDetails.class));
    }

    @Test
    void getTrainerProfileByUsernameShouldReturnOkWhenTrainerExists() throws Exception {
        when(gymFacade.findTrainerByUsername(trainerDTO.getUser().getUsername()))
                .thenReturn(Optional.of(trainerDTO));

        mockMvc.perform(get("/trainers/{username}", trainerDTO.getUser().getUsername()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(new ObjectMapper().writeValueAsString(trainerDTO)));

        verify(gymFacade, times(1)).findTrainerByUsername(trainerDTO.getUser().getUsername());
    }

    @Test
    void updateTrainerProfileShouldReturnOkWhenTrainerIsUpdated() throws Exception {

        TrainerDTO updatedTrainerDTO = new TrainerDTO();

        UserDTO user = new UserDTO();
        user.setUsername("trainer1");
        user.setFirstName("UpdatedTrainer");
        user.setLastName("One");
        updatedTrainerDTO.setUser(user);

        TrainingTypeDTO specialization = new TrainingTypeDTO();
        specialization.setName("Fitness Training");
        updatedTrainerDTO.setSpecialization(specialization);

        when(gymFacade.updateTrainerProfile(anyString(), any(TrainerDTO.class))).thenReturn(updatedTrainerDTO);

        mockMvc.perform(put("/trainers/{username}", updatedTrainerDTO.getUser().getUsername())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedTrainerDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(new ObjectMapper().writeValueAsString(updatedTrainerDTO)));

        verify(gymFacade, times(1)).updateTrainerProfile(anyString(), any(TrainerDTO.class));
    }

    @Test
    void getTrainerTrainingsShouldReturnOkWhenTrainingsExist() throws Exception {
        when(gymFacade.getTrainerTrainingsByCriteria(trainerDTO.getUser().getUsername(), from, to, traineeUsername,
                trainingType)).thenReturn(trainings);

        mockMvc.perform(get("/trainers/{username}/trainings", trainerDTO.getUser().getUsername())
                        .param("from", from.toString())
                        .param("to", to.toString())
                        .param("trainee", traineeUsername)
                        .param("trainingType", trainingType))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].trainingName").value("Morning Yoga"))
                .andExpect(jsonPath("$[1].trainingName").value("Evening Yoga"));

        verify(gymFacade, times(1))
                .getTrainerTrainingsByCriteria(trainerDTO.getUser().getUsername(), from, to, traineeUsername, trainingType);
    }

    @Test
    void activateDeactivateTraineeShouldReturnOkWhenTrainerExists() throws Exception {
        String username = "trainer1";
        boolean isActive = true;

        doNothing().when(gymFacade).activateDeactivateTrainer(username, isActive);

        mockMvc.perform(patch("/trainers/{username}/activate", username)
                        .param("isActive", String.valueOf(isActive)))
                .andExpect(status().isOk());

        verify(gymFacade, times(1)).activateDeactivateTrainer(username, isActive);
    }
}

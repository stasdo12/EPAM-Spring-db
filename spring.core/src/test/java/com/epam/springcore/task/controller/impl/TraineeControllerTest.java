package com.epam.springcore.task.controller.impl;

import com.epam.springcore.task.dto.PassUsernameDTO;
import com.epam.springcore.task.dto.TraineeDTO;
import com.epam.springcore.task.dto.TrainerDTO;
import com.epam.springcore.task.dto.TrainingDTO;
import com.epam.springcore.task.dto.TrainingTypeDTO;
import com.epam.springcore.task.dto.UserDTO;
import com.epam.springcore.task.facade.GymFacade;
import com.epam.springcore.task.mapper.TraineeMapper;
import com.epam.springcore.task.model.Trainee;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.util.*;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TraineeController.class)
class TraineeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GymFacade gymFacade;

    @MockBean
    private TraineeMapper traineeMapper;
    private TraineeDTO traineeDTO;
    private List<TrainerDTO> trainers;
    private PassUsernameDTO passUsernameDTO;
    private List<TrainingDTO> trainings;
    private LocalDate from;
    private LocalDate to;
    private String trainerUsername;
    private String trainingType;

    @BeforeEach
    void setUp() {
        traineeDTO = new TraineeDTO();
        UserDTO user = new UserDTO();
        user.setUsername("john.doe");
        user.setFirstName("john");
        user.setLastName("doe");
        traineeDTO.setUser(user);

        trainers = new ArrayList<>();
        TrainerDTO trainer1 = new TrainerDTO();
        UserDTO trainerUser1 = new UserDTO();
        trainerUser1.setUsername("trainer1");
        trainerUser1.setFirstName("Trainer");
        trainerUser1.setLastName("One");
        trainer1.setUser(trainerUser1);
        trainers.add(trainer1);

        TrainerDTO trainer2 = new TrainerDTO();
        UserDTO trainerUser2 = new UserDTO();
        trainerUser2.setUsername("trainer2");
        trainerUser2.setFirstName("Trainer");
        trainerUser2.setLastName("Two");
        trainer2.setUser(trainerUser2);
        trainers.add(trainer2);

        passUsernameDTO = new PassUsernameDTO();
        passUsernameDTO.setUsername("johndoe");
        passUsernameDTO.setPassword("Password");

        from = LocalDate.of(2024, 1, 1);
        to = LocalDate.of(2024, 12, 31);
        trainerUsername = "trainer1";
        trainingType = "Yoga";
        trainings = new ArrayList<>();

        TrainingDTO training1 = new TrainingDTO();
        training1.setTrainingName("Morning Yoga");
        training1.setDurationMinutes(60);
        training1.setDate(LocalDate.of(2024, 1, 10));
        training1.setTrainer(trainer1);

        TrainingTypeDTO trainingTypeDTO1 = new TrainingTypeDTO();
        trainingTypeDTO1.setName(trainingType);
        training1.setTrainingType(trainingTypeDTO1);
        trainings.add(training1);

        TrainingDTO training2 = new TrainingDTO();
        training2.setTrainingName("Evening Yoga");
        training2.setDurationMinutes(45);
        training2.setDate(LocalDate.of(2024, 1, 20));
        training2.setTrainer(trainer2);

        TrainingTypeDTO trainingTypeDTO2 = new TrainingTypeDTO();
        trainingTypeDTO2.setName(trainingType);
        training2.setTrainingType(trainingTypeDTO2);
        trainings.add(training2);
    }

    @Test
    void registerTraineeShouldReturnCreatedWhenTraineeIsSuccessfullyRegistered() throws Exception {
        when(gymFacade.saveTrainee(any(TraineeDTO.class))).thenReturn(passUsernameDTO);

        mockMvc.perform(post("/api/trainees/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(traineeDTO)))
                .andExpect(status().isCreated());

        verify(gymFacade, times(1)).saveTrainee(any(TraineeDTO.class));
    }

    @Test
    void getTraineeProfileByUsernameShouldReturnOkWhenTraineeExists() throws Exception {
        when(gymFacade.findTraineeByUsername(traineeDTO.getUser().getUsername())).thenReturn(Optional.of(traineeDTO));

        mockMvc.perform(get("/api/trainees/{username}", traineeDTO.getUser().getUsername()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(new ObjectMapper().writeValueAsString(traineeDTO)));

        verify(gymFacade, times(1)).findTraineeByUsername(traineeDTO.getUser().getUsername());
    }

    @Test
    void updateTraineeProfileShouldReturnOkWhenTraineeIsUpdated() throws Exception {
        String username = "john.doe";
        TraineeDTO updatedTraineeDTO = new TraineeDTO();
        UserDTO user = new UserDTO();
        user.setUsername(username);
        user.setFirstName("Johnny");
        updatedTraineeDTO.setUser(user);

        when(gymFacade.updateTraineeProfile(username, traineeDTO)).thenReturn(updatedTraineeDTO);

        mockMvc.perform(put("/api/trainees/{username}", username)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(traineeDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(new ObjectMapper().writeValueAsString(updatedTraineeDTO)));

        verify(gymFacade, times(1)).updateTraineeProfile(username, traineeDTO);
    }

    @Test
    void deleteTraineeShouldReturnOkWhenTraineeIsDeleted() throws Exception {
        String username = "john.doe";

        doNothing().when(gymFacade).deleteTrainee(username);

        mockMvc.perform(delete("/api/trainees/{username}", username))
                .andExpect(status().isOk());

        verify(gymFacade, times(1)).deleteTrainee(username);
    }

    @Test
    void getNotAssignedActiveTrainersShouldReturnOkWhenTraineeExists() throws Exception {
        when(gymFacade.getTrainersNotAssignedToTrainee(traineeDTO.getUser().getUsername())).thenReturn(trainers);

        mockMvc.perform(get("/api/trainees/{username}/not-assigned-trainers",
                        traineeDTO.getUser().getUsername()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].user.username").value("trainer1"))
                .andExpect(jsonPath("$[1].user.username").value("trainer2"));

        verify(gymFacade, times(1)).
                getTrainersNotAssignedToTrainee(traineeDTO.getUser().getUsername());
    }

    @Test
    void getTraineeTrainingsShouldReturnOkWhenTraineeExists() throws Exception {
        when(gymFacade.getTraineeTrainingsByCriteria(traineeDTO.getUser().getUsername(), from, to, trainerUsername,
                trainingType)).thenReturn(trainings);

        mockMvc.perform(get("/api/trainees/{username}/trainings", traineeDTO.getUser().getUsername())
                        .param("from", from.toString())
                        .param("to", to.toString())
                        .param("trainer", trainerUsername)
                        .param("trainingType", trainingType))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].trainingName").value("Morning Yoga"))
                .andExpect(jsonPath("$[0].trainer.user.username").value(trainerUsername))
                .andExpect(jsonPath("$[0].durationMinutes").value(60))
                .andExpect(jsonPath("$[0].date").value("2024-01-10"))
                .andExpect(jsonPath("$[1].trainingName").value("Evening Yoga"))
                .andExpect(jsonPath("$[1].trainer.user.username").value("trainer2"))
                .andExpect(jsonPath("$[1].durationMinutes").value(45))
                .andExpect(jsonPath("$[1].date").value("2024-01-20"));

        verify(gymFacade, times(1))
                .getTraineeTrainingsByCriteria(traineeDTO.getUser()
                        .getUsername(), from, to, trainerUsername, trainingType);
    }

    @Test
    void activateDeactivateTraineeShouldReturnOkWhenTraineeExists() throws Exception {
        String username = "john.doe";
        boolean isActive = true;

        doNothing().when(gymFacade).activateDeactivateTraineeProfile(username, isActive);

        mockMvc.perform(patch("/api/trainees/{username}/activate", username)
                        .param("isActive", String.valueOf(isActive)))
                .andExpect(status().isOk());

        verify(gymFacade, times(1)).activateDeactivateTraineeProfile(username, isActive);
    }

    @Test
    void updateTraineeTrainers_Success() throws Exception {
        String username = "testUser";
        Set<TrainerDTO> trainers = new HashSet<>();
        TrainerDTO trainerDTO = new TrainerDTO();
        trainers.add(trainerDTO);

        Trainee updatedTrainee = new Trainee();
        TraineeDTO updatedTraineeDTO = new TraineeDTO();

        when(gymFacade.updateTraineeTrainers(username, trainers)).thenReturn(updatedTrainee);
        when(traineeMapper.traineeToDTO(updatedTrainee)).thenReturn(updatedTraineeDTO);

        mockMvc.perform(put("/api/trainees/{username}/trainers", username)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[{\"field\": \"value\"}]"))
                .andExpect(status().isOk());

        verify(gymFacade).updateTraineeTrainers(username, trainers);
        verify(traineeMapper).traineeToDTO(updatedTrainee);
    }
}
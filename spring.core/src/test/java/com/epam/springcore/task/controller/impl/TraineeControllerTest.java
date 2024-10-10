package com.epam.springcore.task.controller.impl;

import com.epam.springcore.task.dto.PassUsernameDTO;
import com.epam.springcore.task.dto.TraineeDTO;
import com.epam.springcore.task.dto.TrainerDTO;
import com.epam.springcore.task.dto.TrainingDTO;
import com.epam.springcore.task.dto.TrainingTypeDTO;
import com.epam.springcore.task.dto.UserDTO;
import com.epam.springcore.task.facade.GymFacade;
import com.epam.springcore.task.health.metrics.ExecutionTimeMetrics;
import com.epam.springcore.task.health.metrics.RequestMetrics;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
    private ExecutionTimeMetrics executionTimeMetrics;

    @MockBean
    private RequestMetrics requestMetrics;

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
        traineeDTO = createTraineeDTO();
        trainers = createTrainers();
        passUsernameDTO = createPassUsernameDTO();
        from = LocalDate.of(2024, 1, 1);
        to = LocalDate.of(2024, 12, 31);
        trainerUsername = "trainer1";
        trainingType = "Yoga";
        trainings = createTrainings();
    }

    @Test
    void registerTraineeShouldReturnCreatedWhenTraineeIsSuccessfullyRegistered() throws Exception {
        when(gymFacade.saveTrainee(any(TraineeDTO.class))).thenReturn(passUsernameDTO);

        mockMvc.perform(post("/trainees/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(traineeDTO)))
                .andExpect(status().isCreated());

        verify(gymFacade, times(1)).saveTrainee(any(TraineeDTO.class));
    }

    @Test
    void getTraineeProfileByUsernameShouldReturnOkWhenTraineeExists() throws Exception {
        when(gymFacade.findTraineeByUsername(traineeDTO.getUser().getUsername())).thenReturn(Optional.of(traineeDTO));

        mockMvc.perform(get("/trainees/{username}", traineeDTO.getUser().getUsername()))
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

        mockMvc.perform(put("/trainees/{username}", username)
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

        mockMvc.perform(delete("/trainees/{username}", username))
                .andExpect(status().isNoContent());
    }

    @Test
    void getNotAssignedActiveTrainersShouldReturnOkWhenTraineeExists() throws Exception {
        when(gymFacade.getTrainersNotAssignedToTrainee(traineeDTO.getUser().getUsername())).thenReturn(trainers);

        mockMvc.perform(get("/trainees/{username}/not-assigned-trainers",
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

        mockMvc.perform(get("/trainees/{username}/trainings", traineeDTO.getUser().getUsername())
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

        mockMvc.perform(patch("/trainees/{username}/activate", username)
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

        TraineeDTO updatedTraineeDTO = new TraineeDTO();

        when(gymFacade.updateTraineeTrainers(username, trainers)).thenReturn(updatedTraineeDTO);

        mockMvc.perform(put("/trainees/{username}/trainers", username)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[{\"field\": \"value\"}]"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(gymFacade).updateTraineeTrainers(username, trainers);
    }

    private TraineeDTO createTraineeDTO() {
        TraineeDTO dto = new TraineeDTO();
        UserDTO user = new UserDTO();
        user.setUsername("john.doe");
        user.setFirstName("john");
        user.setLastName("doe");
        dto.setUser(user);
        return dto;
    }

    private List<TrainerDTO> createTrainers() {
        List<TrainerDTO> trainers = new ArrayList<>();
        trainers.add(createTrainerDTO("trainer1", "One"));
        trainers.add(createTrainerDTO("trainer2", "Two"));
        return trainers;
    }

    private TrainerDTO createTrainerDTO(String username, String lastName) {
        TrainerDTO trainerDTO = new TrainerDTO();
        UserDTO user = new UserDTO();
        user.setUsername(username);
        user.setFirstName("Trainer");
        user.setLastName(lastName);
        trainerDTO.setUser(user);
        return trainerDTO;
    }

    private PassUsernameDTO createPassUsernameDTO() {
        PassUsernameDTO dto = new PassUsernameDTO();
        dto.setUsername("johndoe");
        dto.setPassword("Password");
        return dto;
    }

    private List<TrainingDTO> createTrainings() {
        List<TrainingDTO> trainings = new ArrayList<>();
        trainings.add(createTrainingDTO("Morning Yoga", 60, LocalDate.of(2024, 1,
                10), "trainer1"));
        trainings.add(createTrainingDTO("Evening Yoga", 45, LocalDate.of(2024, 1,
                20), "trainer2"));
        return trainings;
    }

    private TrainingDTO createTrainingDTO(String name, int duration, LocalDate date,
                                          String trainerUsername) {
        TrainingDTO trainingDTO = new TrainingDTO();
        trainingDTO.setTrainingName(name);
        trainingDTO.setDurationMinutes(duration);
        trainingDTO.setDate(date);

        TrainerDTO trainerDTO = createTrainerDTO(trainerUsername, "One");
        trainingDTO.setTrainer(trainerDTO);

        TrainingTypeDTO trainingTypeDTO = new TrainingTypeDTO();
        trainingTypeDTO.setName("Yoga");
        trainingDTO.setTrainingType(trainingTypeDTO);

        return trainingDTO;
    }
}
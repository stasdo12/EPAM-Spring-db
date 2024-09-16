package com.epam.springcore.task.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class TraineeDTO {

    @NotNull(message = "User cannot be null")
    private UserDTO user;

    @Past(message = "Birthday must be a date in the past")
    private LocalDate birthday;

    private List<TrainingDTO> trainings;

    private List<TrainerDTO> trainers;

    private String address;
}

package com.epam.springcore.task.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class TrainerDTO {

    @NotNull(message = "User cannot be null")
    private UserDTO user;

    @NotNull(message = "Specialization cannot be null")
    private TrainingTypeDTO specialization;

    private List<TrainingDTO> trainings;

    private Set<TraineeDTO> trainees;
}

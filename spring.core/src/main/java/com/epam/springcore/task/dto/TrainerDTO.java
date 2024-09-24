package com.epam.springcore.task.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainerDTO {

    @NotNull(message = "User cannot be null")
    private UserDTO user;

    @NotNull(message = "Specialization cannot be null")
    private TrainingTypeDTO specialization;

    private List<TrainingDTO> trainings;

    private Set<TraineeDTO> trainees;
}

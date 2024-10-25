package com.epam.springcore.task.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TrainingDTO {

    @NotNull(message = "Trainee cannot be null")
    private TraineeDTO trainee;

    @NotNull(message = "Trainer cannot be null")
    private TrainerDTO trainer;

    @NotBlank(message = "Training name cannot be blank")
    private String trainingName;

    @NotNull(message = "Training type cannot be null")
    private TrainingTypeDTO trainingType;

    @NotNull(message = "Date cannot be null")
    @FutureOrPresent(message = "Date must be in the present or future")
    private LocalDate date;

    @Min(value = 1, message = "Duration must be at least 1 minute")
    private int durationMinutes;

}

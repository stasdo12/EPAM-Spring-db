package com.epam.springcore.task.model;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Training {

    private long trainingId;

    private long traineeId;

    private long trainerId;

    @NotNull(message = "Training type cannot be null")
    private TrainingType trainingType;

    @NotNull(message = "Date cannot be null")
    @FutureOrPresent(message = "Date must be in the present or future")
    private LocalDate date;

    @Min(value = 1, message = "Duration must be at least 1 minute")
    private int durationMinutes;
}
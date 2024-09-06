package com.epam.springcore.task.model;

import lombok.*;

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
    private TrainingType trainingType;
    private LocalDate date;
    private int durationMinutes;
}
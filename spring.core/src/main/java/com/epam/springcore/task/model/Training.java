package com.epam.springcore.task.model;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class Training {
    private long trainingId;
    private long traineeId;
    private long trainerId;
    private TrainingType trainingType;
    private LocalDate date;
    private int durationMinutes;
}
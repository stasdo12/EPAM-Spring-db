package com.epam.springcore.task.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Trainer {

    private long trainerId;

    @NotNull(message = "User cannot be null")
    private User user;

    @NotNull(message = "Specialization cannot be null")
    private TrainingType specialization;

}

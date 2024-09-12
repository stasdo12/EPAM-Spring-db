package com.epam.springcore.task.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
@Entity
@Table(name = "training")
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long trainingId;

    @NotNull(message = "Trainee cannot be null")
    @ManyToOne
    @JoinColumn(name = "trainee_id", nullable = false)
    private Trainee trainee;

    @NotNull(message = "Trainer cannot be null")
    @ManyToOne
    @JoinColumn(name = "trainer_id", nullable = false)
    private Trainer trainer;

    @NotBlank(message = "Training name cannot be blank")
    @Column(name = "training_name", nullable = false)
    private String trainingName;

    @NotNull(message = "Training type cannot be null")
    @ManyToOne
    @JoinColumn(name = "training_type_id", nullable = false)
    private TrainingType trainingType;

    @NotNull(message = "Date cannot be null")
    @FutureOrPresent(message = "Date must be in the present or future")
    @Column(name = "training_date", nullable = false)
    private LocalDate date;

    @Min(value = 1, message = "Duration must be at least 1 minute")
    @Column(name = "training_duration", nullable = false)
    private int durationMinutes;

}
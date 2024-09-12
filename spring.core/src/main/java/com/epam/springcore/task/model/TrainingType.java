package com.epam.springcore.task.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "training_type")
public class TrainingType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Training type name cannot be blank")
    @Column(name = "training_type_name")
    private String name;

    @OneToMany(mappedBy = "specialization", cascade = CascadeType.ALL)
    private List<Trainer> trainers;

    @OneToMany(mappedBy = "trainingType", cascade = CascadeType.ALL)
    private List<Training> trainings;
}

package com.epam.springcore.task.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
    @Column(name = "id")
    private Long id;

    @Column(name = "training_type_name")
    private String name;

    @OneToMany(mappedBy = "specialization", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Trainer> trainers;

    @OneToMany(mappedBy = "trainingType", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Training> trainings;

    @Override
    public String toString() {
        return "TrainingType{id=" + id + ", name='" + name + "'}";
    }
}

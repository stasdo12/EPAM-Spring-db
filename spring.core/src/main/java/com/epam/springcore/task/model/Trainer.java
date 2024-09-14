package com.epam.springcore.task.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor

@EqualsAndHashCode
@Entity
@Table(name = "trainer")
public class Trainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long trainerId;

    @NotNull(message = "User cannot be null")
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "training_type_id", nullable = false)
    @NotNull(message = "Specialization cannot be null")
    private TrainingType specialization;

    @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL)
    private List<Training> trainings;

    @ManyToMany(mappedBy = "trainers", fetch = FetchType.EAGER)
    private Set<Trainee> trainees;

    @Override
    public String toString() {
        return "Trainer{id=" + trainerId + ", user=" + user.getUsername() + ", " +
                "specialization=" + specialization.getName() + "}";
    }
}

package com.epam.springcore.task.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "trainee")
public class Trainee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long traineeId;

    @NotNull(message = "User cannot be null")
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Past(message = "Birthday must be a date in the past")
    @Column(name = "date_of_birth")
    private LocalDate birthday;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "trainee", cascade = CascadeType.ALL)
    private List<Training> trainings;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "trainee_has_trainer",
            joinColumns = @JoinColumn(name = "trainee_id"),
            inverseJoinColumns = @JoinColumn(name = "trainer_id")
    )
    private Set<Trainer> trainers;


    @Override
    public String toString() {
        return "Trainee{" +
                "id=" + traineeId +
                ", user=" + (user != null ? user.getUsername() : "null") +
                '}';
    }
}

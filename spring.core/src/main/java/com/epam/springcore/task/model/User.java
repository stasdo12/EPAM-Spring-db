package com.epam.springcore.task.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @NotBlank(message = "First name cannot be blank")
    @Size(max = 50, message = "First name must not exceed 50 characters")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    @Size(max = 50, message = "Last name must not exceed 50 characters")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters")
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 10, message = "Password must be at least 10 characters long")
    @Column(name = "password", nullable = false)
    private String password;

    @JsonProperty("isActive")
    @NotNull(message = "Active status must not be null")
    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Trainee trainee;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Trainer trainer;

    @Override
    public String toString() {
        return "User{" +
                "id=" + userId +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

}

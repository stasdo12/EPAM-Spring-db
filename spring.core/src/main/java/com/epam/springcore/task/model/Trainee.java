package com.epam.springcore.task.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
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
public class Trainee {

    private long traineeId;

    @NotNull(message = "User cannot be null")
    private User user;

    @NotNull(message = "Birthday cannot be null")
    @Past(message = "Birthday must be a date in the past")
    private LocalDate birthday;

    @NotBlank(message = "Address cannot be blank")
    private String address;

}

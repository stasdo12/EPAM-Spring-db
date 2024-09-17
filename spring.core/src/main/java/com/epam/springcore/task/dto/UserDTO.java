package com.epam.springcore.task.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data

public class UserDTO {

    @NotBlank(message = "Username cannot be blank")
    @Pattern(regexp = "^[a-zA-Z]{3,30}$", message = "Username must be between 3 and 30 characters and contain only letters")
    private String username;

    @NotBlank(message = "First name cannot be blank")
    @Pattern(regexp = "^[a-zA-Z]{3,30}$", message = "FirstName must be between 3 and 30 characters and contain only letters")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    @Pattern(regexp = "^[a-zA-Z]{3,30}$", message = "LastName must be between 3 and 30 characters and contain only letters")
    private String lastName;

}

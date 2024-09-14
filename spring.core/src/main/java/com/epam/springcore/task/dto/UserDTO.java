package com.epam.springcore.task.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTO {

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters")
    private String username;

    @NotBlank(message = "First name cannot be blank")
    @Size(max = 50, message = "First name must not exceed 50 characters")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    @Size(max = 50, message = "Last name must not exceed 50 characters")
    private String lastName;

}

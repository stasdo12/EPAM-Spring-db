package com.epam.springcore.task.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PassUsernameDTO {

    @NotNull(message = "username is required")
    private String username;

    @NotNull(message = "Password is required")
    private String password;
}

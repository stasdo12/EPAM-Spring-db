package com.epam.springcore.task.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassUsernameDTO {
    private String username;

    private String password;
}

package com.epam.springcore.task.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtRequest {

    private String username;

    private String password;

}
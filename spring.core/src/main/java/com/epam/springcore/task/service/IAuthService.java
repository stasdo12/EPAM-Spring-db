package com.epam.springcore.task.service;

import com.epam.springcore.task.dto.JwtRequest;
import com.epam.springcore.task.dto.JwtResponse;
import org.springframework.web.bind.annotation.RequestBody;

public interface IAuthService {

    JwtResponse createAuthToken(@RequestBody JwtRequest authRequest);

    boolean logout(String token);
}

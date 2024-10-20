package com.epam.springcore.task.service;


import com.epam.springcore.task.dto.JwtRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface IAuthService {

    ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest);
}

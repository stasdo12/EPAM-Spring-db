package com.epam.springcore.task.controller.impl;


import com.epam.springcore.task.dto.JwtRequest;
import com.epam.springcore.task.dto.JwtResponse;
import com.epam.springcore.task.service.impl.AuthService;
import com.epam.springcore.task.service.impl.UserDetailsServiceImpl;
import com.epam.springcore.task.utils.impl.JwtTokenUtils;
import exception.AppError;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthController {

  private final AuthService authService;


    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest){
        return authService.createAuthToken(authRequest);
    }

}

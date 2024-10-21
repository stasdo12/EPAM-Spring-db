package com.epam.springcore.task.service.impl;

import com.epam.springcore.task.dto.JwtRequest;
import com.epam.springcore.task.dto.JwtResponse;
import com.epam.springcore.task.service.IAuthService;
import com.epam.springcore.task.utils.impl.JwtTokenUtils;
import exception.AppError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AuthService implements IAuthService {

    private final UserDetailsServiceImpl userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;

    private final BlackListService blackListService;



    public AuthService(UserDetailsServiceImpl userService, JwtTokenUtils jwtTokenUtils, AuthenticationManager authenticationManager,
                       BlackListService blackListService) {
        this.userService = userService;
        this.jwtTokenUtils = jwtTokenUtils;
        this.authenticationManager = authenticationManager;
        this.blackListService = blackListService;
    }

    @Override
    public ResponseEntity<Object> createAuthToken(@RequestBody JwtRequest authRequest){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
                    authRequest.getPassword()));
        }catch (BadCredentialsException ex){
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED, "Incorrect login or password"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @Override
    public boolean logout(String token) {
        blackListService.addToBlacklist(token);
        return true;
    }
}

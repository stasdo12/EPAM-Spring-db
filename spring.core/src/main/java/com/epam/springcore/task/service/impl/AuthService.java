package com.epam.springcore.task.service.impl;

import com.epam.springcore.task.dto.JwtRequest;
import com.epam.springcore.task.dto.JwtResponse;
import com.epam.springcore.task.service.IAuthService;
import com.epam.springcore.task.utils.impl.JwtTokenUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

@Service
public class AuthService implements IAuthService {
    private final UserDetailsServiceImpl userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;
    private final BlackListService blackListService;
    private final LoginAttemptService loginAttemptService;

    public AuthService(UserDetailsServiceImpl userService, JwtTokenUtils jwtTokenUtils,
                       AuthenticationManager authenticationManager,
                       BlackListService blackListService, LoginAttemptService loginAttemptService) {
        this.userService = userService;
        this.jwtTokenUtils = jwtTokenUtils;
        this.authenticationManager = authenticationManager;
        this.blackListService = blackListService;
        this.loginAttemptService = loginAttemptService;
    }

    @Override
    public JwtResponse createAuthToken(@RequestBody JwtRequest authRequest) {
        String username = authRequest.getUsername();
        if (loginAttemptService.isBlocked(username)) {
            throw new ResponseStatusException(HttpStatus.LOCKED,
                    "User is temporarily blocked due to multiple failed login attempts. Please try again later.");
        }
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
                    authRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            loginAttemptService.loginSucceeded(username);
        } catch (BadCredentialsException ex) {
            loginAttemptService.loginFailed(username);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect login or password");
        }

        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);

        return new JwtResponse(token);
    }

    @Override
    public boolean logout(String token) {
        blackListService.addToBlacklist(token);
        return true;
    }

    public String getJwtToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getCredentials() != null) {
            return (String) authentication.getCredentials();
        } else {
            throw new IllegalStateException("JWT token is missing in the SecurityContext.");
        }
    }
}
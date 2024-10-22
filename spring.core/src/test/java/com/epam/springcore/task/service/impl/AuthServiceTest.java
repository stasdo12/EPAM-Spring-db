package com.epam.springcore.task.service.impl;

import com.epam.springcore.task.dto.JwtRequest;
import com.epam.springcore.task.dto.JwtResponse;
import com.epam.springcore.task.utils.impl.JwtTokenUtils;
import exception.AppError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserDetailsServiceImpl userService;
    @Mock
    private JwtTokenUtils jwtTokenUtils;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private BlackListService blackListService;

    @Mock
    private LoginAttemptService loginAttemptService;

    private final String username = "testUser";
    private final String password = "password123";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateAuthTokenSuccessfully() {
        JwtRequest authRequest = new JwtRequest(username, password);
        UserDetails userDetails = mock(UserDetails.class);
        String token = "jwt.token.here";

        when(loginAttemptService.isBlocked(username)).thenReturn(false);
        when(authenticationManager.authenticate(any())).thenReturn(null);
        when(userService.loadUserByUsername(username)).thenReturn(userDetails);
        when(jwtTokenUtils.generateToken(userDetails)).thenReturn(token);

        ResponseEntity<Object> response = authService.createAuthToken(authRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(token, ((JwtResponse) Objects.requireNonNull(response.getBody())).getToken());
        verify(loginAttemptService).loginSucceeded(username);
    }

    @Test
    void shouldReturnLockedWhenUserIsBlocked() {

        JwtRequest authRequest = new JwtRequest(username, password);

        when(loginAttemptService.isBlocked(username)).thenReturn(true);

        ResponseEntity<Object> response = authService.createAuthToken(authRequest);

        assertEquals(HttpStatus.LOCKED, response.getStatusCode());
        assertEquals("User is temporarily blocked due to multiple failed login attempts. Please try again later.",
                ((AppError) Objects.requireNonNull(response.getBody())).getMessage());
    }

    @Test
    void shouldReturnUnauthorizedWhenBadCredentials() {
        JwtRequest authRequest = new JwtRequest(username, password);

        when(loginAttemptService.isBlocked(username)).thenReturn(false);
        doThrow(new BadCredentialsException("")).when(authenticationManager).authenticate(any());

        ResponseEntity<Object> response = authService.createAuthToken(authRequest);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Incorrect login or password",
                ((AppError) Objects.requireNonNull(response.getBody())).getMessage());
        verify(loginAttemptService).loginFailed(username);
    }

    @Test
    void shouldBlacklistTokenOnLogout() {
        String token = "jwt.token.here";

        boolean result = authService.logout(token);

        assertTrue(result);
        verify(blackListService).addToBlacklist(token);
    }
}
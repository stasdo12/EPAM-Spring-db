package com.epam.springcore.task.service.impl;

import com.epam.springcore.task.dto.JwtRequest;
import com.epam.springcore.task.dto.JwtResponse;
import com.epam.springcore.task.utils.impl.JwtTokenUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
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

        JwtResponse response = authService.createAuthToken(authRequest);

        assertEquals(token, response.getToken());
        verify(loginAttemptService).loginSucceeded(username);
    }

    @Test
    void shouldReturnLockedWhenUserIsBlocked() {

        JwtRequest authRequest = new JwtRequest(username, password);

        when(loginAttemptService.isBlocked(username)).thenReturn(true);

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
            authService.createAuthToken(authRequest);
        });

        assertEquals(HttpStatus.LOCKED, thrown.getStatusCode());
        assertEquals("User is temporarily blocked due to multiple " +
                "failed login attempts. Please try again later.", thrown.getReason());
    }

    @Test
    void shouldReturnUnauthorizedWhenBadCredentials() {
        JwtRequest authRequest = new JwtRequest(username, password);

        when(loginAttemptService.isBlocked(username)).thenReturn(false);
        doThrow(new BadCredentialsException("")).when(authenticationManager).authenticate(any());

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
            authService.createAuthToken(authRequest);
        });

        assertEquals(HttpStatus.UNAUTHORIZED, thrown.getStatusCode());
        assertEquals("Incorrect login or password", thrown.getReason());
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
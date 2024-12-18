package com.epam.springcore.task.filter;

import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.Test;

import com.epam.springcore.task.service.impl.BlackListService;
import com.epam.springcore.task.utils.impl.JwtTokenUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;

import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class JwtRequestFilterTest {

    private JwtRequestFilter jwtRequestFilter;
    @Mock
    private JwtTokenUtils jwtTokenUtils;
    @Mock
    private BlackListService blackListService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain filterChain;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtRequestFilter = new JwtRequestFilter(jwtTokenUtils, blackListService);
    }

    @Test
    void shouldAuthenticateUserWhenTokenIsValid() throws ServletException, IOException {
        String token = "Bearer valid_token";
        String username = "testUser";

        when(request.getHeader("Authorization")).thenReturn(token);
        when(jwtTokenUtils.getUsername("valid_token")).thenReturn(username);
        when(blackListService.isTokenBlacklisted("valid_token")).thenReturn(false);

        SecurityContext mockSecurityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(mockSecurityContext);

        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        ArgumentCaptor<UsernamePasswordAuthenticationToken> authenticationCaptor =
                ArgumentCaptor.forClass(UsernamePasswordAuthenticationToken.class);
        verify(filterChain).doFilter(request, response);
        verify(mockSecurityContext).setAuthentication(authenticationCaptor.capture());

        UsernamePasswordAuthenticationToken authenticationToken = authenticationCaptor.getValue();
        assert authenticationToken.getName().equals(username);
    }

    @Test
    void shouldReturnUnauthorizedWhenTokenIsBlacklisted() throws ServletException, IOException {
        String token = "Bearer blacklisted_token";

        when(request.getHeader("Authorization")).thenReturn(token);
        when(blackListService.isTokenBlacklisted("blacklisted_token")).thenReturn(true);

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
            jwtRequestFilter.doFilterInternal(request, response, filterChain);
        });


        assertEquals(HttpStatus.UNAUTHORIZED, thrown.getStatusCode());
        assertEquals("JWT token is blacklisted", thrown.getReason());
        verify(filterChain, never()).doFilter(request, response);
    }

    @Test
    void shouldNotAuthenticateUserWhenTokenIsExpired() throws ServletException, IOException {
        String token = "Bearer expired_token";

        when(request.getHeader("Authorization")).thenReturn(token);
        when(jwtTokenUtils.getUsername("expired_token")).
                thenThrow(new ExpiredJwtException(null, null, "Expired token", null));

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> {
            jwtRequestFilter.doFilterInternal(request, response, filterChain);
        });

        assertEquals(HttpStatus.UNAUTHORIZED, thrown.getStatusCode());
        assertEquals("JWT token is expired", thrown.getReason());
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void shouldReturnUnauthorizedWhenTokenIsMissing() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn(null);

        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assert SecurityContextHolder.getContext().getAuthentication() == null;
    }
}
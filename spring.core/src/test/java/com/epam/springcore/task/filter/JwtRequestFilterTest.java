package com.epam.springcore.task.filter;

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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

import static org.mockito.Mockito.*;

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

        // Mock the necessary behaviors
        when(request.getHeader("Authorization")).thenReturn(token);
        when(jwtTokenUtils.getUsername("valid_token")).thenReturn(username);
        when(blackListService.isTokenBlacklisted("valid_token")).thenReturn(false);

        // Create a mock SecurityContext
        SecurityContext mockSecurityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(mockSecurityContext); // Set the mock context

        // Execute the filter
        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        ArgumentCaptor<UsernamePasswordAuthenticationToken> authenticationCaptor = ArgumentCaptor.forClass(UsernamePasswordAuthenticationToken.class);
        verify(filterChain).doFilter(request, response); // Verify that the filter chain was called
        verify(mockSecurityContext).setAuthentication(authenticationCaptor.capture()); // Verify setAuthentication was called on the mock context

        UsernamePasswordAuthenticationToken authenticationToken = authenticationCaptor.getValue();
        assert authenticationToken.getName().equals(username); // Assert the authentication token's name
    }

    @Test
    void shouldReturnUnauthorizedWhenTokenIsBlacklisted() throws ServletException, IOException {
        String token = "Bearer blacklisted_token";

        when(request.getHeader("Authorization")).thenReturn(token);
        when(blackListService.isTokenBlacklisted("blacklisted_token")).thenReturn(true);

        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(filterChain, never()).doFilter(request, response);
    }

    @Test
    void shouldNotAuthenticateUserWhenTokenIsExpired() throws ServletException, IOException {
        String token = "Bearer expired_token";
        String username = "testUser";

        when(request.getHeader("Authorization")).thenReturn(token);
        when(jwtTokenUtils.getUsername("expired_token")).thenThrow(new io.jsonwebtoken.ExpiredJwtException(null, null, "Expired token", null));

        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assert SecurityContextHolder.getContext().getAuthentication() == null;
    }

    @Test
    void shouldReturnUnauthorizedWhenTokenIsMissing() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn(null);

        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response); // Should proceed with the filter chain
        assert SecurityContextHolder.getContext().getAuthentication() == null; // Should not set authentication
    }
}
package com.epam.springcore.task.filter;

import com.epam.springcore.task.service.impl.BlackListService;
import com.epam.springcore.task.utils.impl.JwtTokenUtils;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Component
@RequiredArgsConstructor
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtTokenUtils jwtTokenUtils;
    private final BlackListService blackListService;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = req.getHeader("Authorization");
        String username = null;
        String jwt = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            try {
                if (blackListService.isTokenBlacklisted(jwt)) {
                    log.debug("JWT token is blacklisted");
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "JWT token is blacklisted");
                }

                username = jwtTokenUtils.getUsername(jwt);
            } catch (ExpiredJwtException e) {
                log.debug("JWT token is expired, create new one if needed");
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "JWT token is expired");
            }
        }

        if (username != null && blackListService.isTokenBlacklisted(username)){
            log.debug("User is temporarily blocked due to multiple failed login attempts.");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is temporarily blocked");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    authorities
            );
            SecurityContextHolder.getContext().setAuthentication(token);
        }

        filterChain.doFilter(req, res);
    }
}

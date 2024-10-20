package com.epam.springcore.task.service.impl;


import com.epam.springcore.task.service.IJwtService;
import com.epam.springcore.task.utils.impl.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService implements IJwtService {
    private final JwtTokenUtils jwtTokenUtils;

    @Autowired
    public JwtService(JwtTokenUtils jwtTokenUtils) {
        this.jwtTokenUtils = jwtTokenUtils;
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return jwtTokenUtils.generateToken(userDetails);
    }
}

package com.exerciseday.dev.src.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.exerciseday.dev.utils.JwtService;


@Service
public class AuthProvider {
    private final AuthDao authDao;
    private final JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    public AuthProvider(AuthDao authDao, JwtService jwtService){
        this.authDao = authDao;
        this.jwtService = jwtService;
    }
}

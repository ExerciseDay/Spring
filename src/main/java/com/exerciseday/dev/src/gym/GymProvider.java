package com.exerciseday.dev.src.gym;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.slf4j.Logger;

import org.springframework.stereotype.Service;

import com.exerciseday.dev.utils.JwtService;

@Service
public class GymProvider {
    
    private final GymDao gymDao;
    private final JwtService jwtService;


    
    
}

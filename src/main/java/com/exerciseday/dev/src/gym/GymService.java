package com.exerciseday.dev.src.gym;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.exerciseday.dev.src.gym.model.*;

@Service
public class GymService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final GymDao gymDao;
    
    public GymService(GymDao gymDao){
        this.gymDao = gymDao;
    }
}

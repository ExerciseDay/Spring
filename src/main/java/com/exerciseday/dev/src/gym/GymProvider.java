package com.exerciseday.dev.src.gym;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;



import com.exerciseday.dev.src.gym.model.*;

@Service
public class GymProvider {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final GymDao gymDao;
    
    public GymProvider(GymDao gymDao){
        this.gymDao = gymDao;
    }
}




package com.exerciseday.dev.src.custom;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class CustomProvider {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final CustomDao customDao;
    
    public CustomProvider(CustomDao customDao){
        this.customDao = customDao;
    }


}

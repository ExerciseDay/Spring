package com.exerciseday.dev.src.exercise;

import org.springframework.stereotype.Service;

import com.exerciseday.dev.config.BaseException;
import com.exerciseday.dev.config.BaseResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class ExerciseProvider {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private ExerciseDao exerciseDao;
    public ExerciseProvider(ExerciseDao exerciseDao){
        this.exerciseDao = exerciseDao;
    }

}

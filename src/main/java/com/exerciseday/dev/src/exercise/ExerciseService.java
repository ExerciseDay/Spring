package com.exerciseday.dev.src.exercise;

import org.springframework.stereotype.Service;

import com.exerciseday.dev.config.BaseException;
import com.exerciseday.dev.config.BaseResponseStatus;
import com.exerciseday.dev.src.exercise.model.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class ExerciseService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private ExerciseDao exercisetDao;
    private ExerciseProvider exercisetProvider;
    public ExerciseService(ExerciseDao exercisetDao, ExerciseProvider exercisetProvider){
        this.exercisetDao = exercisetDao;
        this.exercisetProvider = exercisetProvider;
    }




}

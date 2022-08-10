package com.exerciseday.dev.src.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.exerciseday.dev.config.BaseException;
import com.exerciseday.dev.config.BaseResponse;
import com.exerciseday.dev.config.BaseResponseStatus;
import com.exerciseday.dev.src.exercise.model.*;
import com.exerciseday.dev.utils.JwtService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
@RequestMapping("exercise")
public class ExerciseController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ExerciseProvider exerciseProvider;
    @Autowired
    private ExerciseService exerciseService;
    @Autowired
    private JwtService jwtService;

    public ExerciseController(ExerciseProvider exerciseProvider, ExerciseService exerciseService, JwtService jwtService){
        this.exerciseProvider = exerciseProvider;
        this.exerciseService = exerciseService;
        this.jwtService = jwtService;
    }

    

}

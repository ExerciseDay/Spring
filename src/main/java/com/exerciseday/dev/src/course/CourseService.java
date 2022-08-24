package com.exerciseday.dev.src.course;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.exerciseday.dev.utils.JwtService;
import com.exerciseday.dev.src.course.model.*;


@Service
public class CourseService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final CourseDao courseDao;
    private final CourseProvider courseProvider;
    private final JwtService jwtService;

    public CourseService(CourseDao courseDao, CourseProvider courseProvider, JwtService jwtService){
        this.courseDao = courseDao;
        this.courseProvider = courseProvider;
        this.jwtService = jwtService;
    }





}

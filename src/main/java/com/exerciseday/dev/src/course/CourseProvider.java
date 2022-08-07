package com.exerciseday.dev.src.course;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.exerciseday.dev.utils.JwtService;
import com.exerciseday.dev.src.course.model.*;
@Service
public class CourseProvider {
    private final CourseDao courseDao;
    private final JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    public CourseProvider(CourseDao courseDao, JwtService jwtService){
        this.courseDao = courseDao;
        this.jwtService = jwtService;
    }


}

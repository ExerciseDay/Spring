package com.exerciseday.dev.src.course;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exerciseday.dev.utils.JwtService;
import com.exerciseday.dev.src.course.model.*;


@RestController
@RequestMapping("/course")
public class CourseController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final CourseProvider courseProvider;

    @Autowired
    private final CourseService courseService;

    @Autowired
    private final JwtService jwtService;

    public CourseController(CourseProvider courseProvider, CourseService courseService, JwtService jwtService){
        this.courseProvider = courseProvider;
        this.courseService = courseService;
        this.jwtService = jwtService;
    }

}

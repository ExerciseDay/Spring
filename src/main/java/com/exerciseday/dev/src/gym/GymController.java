package com.exerciseday.dev.src.gym;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.exerciseday.dev.src.course.model.*;
@RestController
@RequestMapping("/gym")
public class GymController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final GymProvider gymProvider;
    private final GymService gymServce;
    
    public GymController(GymProvider gymProvider,GymService gymService){
        this.gymProvider = gymProvider;
        this.gymServce = gymService;
    }




    
}

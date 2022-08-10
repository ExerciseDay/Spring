package com.exerciseday.dev.src.gym;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.exerciseday.dev.config.BaseException;
import com.exerciseday.dev.config.BaseResponse;
import com.exerciseday.dev.src.gym.model.*;
import com.exerciseday.dev.utils.JwtService;

@RestController
@RequestMapping("/gyms")
public class GymController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private final GymProvider gymProvider;
    @Autowired
    private final GymService gymService;
    @Autowired
    private final JwtService jwtService;

    public GymController(GymProvider gymProvider, GymService gymService, JwtService jwtService){
        this.gymProvider = gymProvider;
        this.gymService = gymService;
        this.jwtService = jwtService;
    }

    @ResponseBody
    @PostMapping("")
    public BaseResponse<GetGymListRes> getGymList(int gymIdx){

        try{
            GetGymListRes getGymList = gymProvider.retrieveGymList(gymIdx);
            return new BaseResponse<>(getGymList);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    
}

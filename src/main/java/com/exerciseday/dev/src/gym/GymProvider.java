package com.exerciseday.dev.src.gym;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.exerciseday.dev.config.BaseException;
import com.exerciseday.dev.src.gym.model.*;
import com.exerciseday.dev.utils.JwtService;

import static com.exerciseday.dev.config.BaseResponseStatus.*;

@Service
public class GymProvider {
    
    private final GymDao gymDao;
    private final JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public GymProvider(GymDao gymDao, JwtService jwtService){
        this.gymDao = gymDao;
        this.jwtService = jwtService;
    }

    public GetGymListRes retrieveGymList(int gymIdx) throws BaseException {
        try{
            List<GetGymRes> getGym = gymDao.selectGym(gymIdx);
            GetGymListRes getGymList = new GetGymListRes(getGym);
            return getGymList;
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }


    
    
}

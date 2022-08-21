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
public class GymService {

    private final GymDao gymDao;
    private final GymProvider gymProvider;
    private final JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public GymService(GymDao gymDao, GymProvider gymProvider, JwtService jwtService){
        this.gymDao = gymDao;
        this.gymProvider = gymProvider;
        this.jwtService = jwtService;
    }

    public GetGymDetailRes retrieveGymDetail(int gymIdx) throws BaseException {
        try{

            GetGymInfoRes getGymInfo = gymDao.selectGymInfo(gymIdx);
            List<GetTrainersRes> getTrainers = gymDao.selectTrainers(gymIdx);
            List<GetReviewRes> getReviews = gymDao.selectReview(gymIdx);
            List<GetImgRes> getImgs = gymDao.selectImgs(gymIdx);
            GetGymDetailRes getGymDetail = new GetGymDetailRes(getGymInfo,getTrainers, getReviews, getImgs);
            return getGymDetail;

        } catch(Exception exception){
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public PostReviewRes updateReview(int userIdx, PostReviewReq postReviewReq) throws BaseException{
        try{

            PostReviewRes postReviewRes = gymService.updateReview(postReviewReq);

            return new BaseResponse<>(postReviewRes);

        } catch (BaseException exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}

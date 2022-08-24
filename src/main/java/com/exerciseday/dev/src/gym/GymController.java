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
    @GetMapping("")
    public BaseResponse<GetGymListRes> getGymList(@RequestParam String univ){

        try{
            GetGymListRes getGymList = gymProvider.retrieveGymList(univ);
            return new BaseResponse<>(getGymList);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("/search")
    public BaseResponse<GetGymListRes> searchGym(@RequestParam String keyword){
        try{
            GetGymListRes searchGymList = gymProvider.searchGymList(keyword);
            return new BaseResponse<>(searchGymList);
        } catch(BaseException exception){
            System.out.println(exception);
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    @ResponseBody
    @GetMapping("/{gymIdx}")
    public BaseResponse<GetGymDetailRes> getGymDetail(@PathVariable("gymIdx") int gymIdx){
        try{

            GetGymDetailRes getGymDetail = gymProvider.retrieveGymDetail(gymIdx);

            return new BaseResponse<>(getGymDetail);

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("/{gymIdx}/review")
    public BaseResponse<String> updateReview(@PathVariable("gymIdx") int gymIdx, @RequestParam String rvContent, @RequestParam int rvSP){
        try{

            int userIdxByJwt = jwtService.getUserIdx();
            gymService.updateReview(userIdxByJwt, gymIdx, rvContent, rvSP);

            String result = "리뷰가 등록되었습니다";
            return new BaseResponse<>(result);

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    
}

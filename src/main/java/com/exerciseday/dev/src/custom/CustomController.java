package com.exerciseday.dev.src.custom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.exerciseday.dev.config.BaseResponse;
import com.exerciseday.dev.config.BaseResponseStatus;
import com.exerciseday.dev.src.custom.model.*;
import com.exerciseday.dev.utils.JwtService;
@RestController
@RequestMapping("/custom")
public class CustomController {
    final Logger logger= LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CustomProvider cCourseProvider;
    @Autowired
    private CustomService cCourseService;
    @Autowired
    private JwtService jwtService;

    public CustomController(CustomProvider cCourseProvider, CustomService cCourseService, JwtService jwtService){
        this.cCourseProvider = cCourseProvider;
        this.cCourseService = cCourseService;
        this.jwtService = jwtService;
    }

    /*
     * 커스텀 코스 생성 API
     * [POST] /custom
     */




     /*
      * 커스텀 코스 조회 API
      * [GET] /custom
      */






     /*
      * 커스텀 코스 변경 API
      * [PATCH] /custom
      */





      /*
       * 커스텀 코스 삭제 API
       * [DELETE] 
       */




}

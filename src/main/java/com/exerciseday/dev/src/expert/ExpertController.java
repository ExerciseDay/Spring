package com.exerciseday.dev.src.expert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.exerciseday.dev.config.BaseException;
import com.exerciseday.dev.config.BaseResponse;
import com.exerciseday.dev.config.BaseResponseStatus;
import com.exerciseday.dev.src.expert.model.*;
import com.exerciseday.dev.utils.JwtService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
@RequestMapping("expert")
public class ExpertController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ExpertProvider expertProvider;
    @Autowired
    private ExpertService expertService;
    @Autowired
    private JwtService jwtService;

    public ExpertController(ExpertProvider expertProvider, ExpertService expertService, JwtService jwtService){
        this.expertProvider = expertProvider;
        this.expertService = expertService;
        this.jwtService = jwtService;
    }

    /*
     * 전문가 코스 생성 API
     * [POST] /expert
     */
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostExpertRes> createExpert(@RequestBody PostExpertReq postExpertReq){
        try{
            if(postExpertReq.getExpertName().length() > 45){
                return new BaseResponse<>(BaseResponseStatus.INVALID_NAME);
            }
            if(postExpertReq.getExpertRoutines().size() < 1){
                return new BaseResponse<>(BaseResponseStatus.EMPTY_ROUTINE);
            }

            PostExpertRes postExpertRes = new PostExpertRes(expertService.createExpert(postExpertReq),postExpertReq.getExpertName());
            return new BaseResponse<>(postExpertRes);
        }
        catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }


    /*
     * 전문가 코스 조회 API
     * [GET] /expert/{expertIdx}
     */
    @ResponseBody
    @GetMapping("/{expertIdx}")
    public BaseResponse<GetExpertRes> getExpert(@PathVariable("expertIdx") int expertIdx){
        try{
            GetExpertRes expert = expertProvider.getExpert(expertIdx);
            return new BaseResponse<>(expert);
        }
        catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }


    /*
     * 부위별 전문가 코스 목록 조회 API
     * [GET] /expert/part
     */
    @ResponseBody
    @GetMapping("/part")
    public BaseResponse<GetExpertByPartRes> getExpertsByPart(@RequestParam int page, @RequestBody GetExpertByPartReq getExpertByPartReq){

        try{

            List<ExpertByPart> expertList = expertProvider.getExpertsByPart(getExpertByPartReq,page);
            GetExpertByPartRes getExpertByPartRes = new GetExpertByPartRes(getExpertByPartReq.getPart(),getExpertByPartReq.getDetailPart(), expertList);
            return new BaseResponse<>(getExpertByPartRes);
        }
        catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

}

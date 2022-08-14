package com.exerciseday.dev.src.custom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.exerciseday.dev.config.BaseException;
import com.exerciseday.dev.config.BaseResponse;
import com.exerciseday.dev.config.BaseResponseStatus;
import com.exerciseday.dev.src.custom.model.*;
import com.exerciseday.dev.utils.JwtService;
@RestController
@RequestMapping("/users/{userIdx}/custom")
public class CustomController {
    final Logger logger= LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CustomProvider customProvider;
    @Autowired
    private CustomService customService;
    @Autowired
    private JwtService jwtService;

    public CustomController(CustomProvider customProvider, CustomService customService, JwtService jwtService){
        this.customProvider = customProvider;
        this.customService = customService;
        this.jwtService = jwtService;
    }

    /*
     * 커스텀 코스 생성 API
     * [POST] /users/{userIdx}/custom
     */
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostCustomRes> createCustom(@PathVariable("userIdx") int userIdx, @RequestBody PostCustomReq postCustomReq){
        if(postCustomReq.getCustomName()==null){
            return new BaseResponse<>(BaseResponseStatus.EMPTY_NAME);
        }

        if(postCustomReq.getCustomName().length() > 45){
            return new BaseResponse<>(BaseResponseStatus.INVALID_NAME);
        }
        /*
        if(postCustomReq.getCustomPart()==null){
            return new BaseResponse<>(BaseResponseStatus.EMPTY_PART);
        }
        if(postCustomReq.getCustomDetailPart()==null){
            return new BaseResponse<>(BaseResponseStatus.EMPTY_DETAIL);
        }
        */
        if(postCustomReq.getCustomRoutines().size() < 1){
            return new BaseResponse<>(BaseResponseStatus.EMPTY_ROUTINE);
        }
        
        try{
            int userIdxByJwt = jwtService.getUserIdx();
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(BaseResponseStatus.INVALID_JWT);
            }

            PostCustomRes postCustomRes = new PostCustomRes(customService.createCustom(userIdx, postCustomReq), userIdx);
            return new BaseResponse<>(postCustomRes);
        }
        catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

     /*
      * 커스텀 코스 조회 API
      * [GET] /users/{userIdx}/custom/{customIdx}
      */
    @ResponseBody
    @GetMapping("/{customIdx}")
    public BaseResponse<GetCustomRes> getCustom(@PathVariable("userIdx") int userIdx, @PathVariable("customIdx") int customIdx){
        try{
            int userIdxByJwt = jwtService.getUserIdx();
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(BaseResponseStatus.INVALID_JWT);
            }

            GetCustomRes custom = customProvider.getCustom(userIdx, customIdx);
            return new BaseResponse<>(custom);
        }
        catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

      /*
       * 커스텀 코스 삭제 API
       * [DELETE] /users/{userIdx}/custom/{customIdx}
       */
    @ResponseBody
    @DeleteMapping("/{customIdx}")
    public BaseResponse<String> deleteCustom(@PathVariable("userIdx") int userIdx, @PathVariable("customIdx") int customIdx){
        try{
            int userIdxByJwt = jwtService.getUserIdx();
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(BaseResponseStatus.INVALID_JWT);
            }

            customService.deleteCustom(userIdx,customIdx);
            String result = "커스텀 코스 삭제 성공";
            return new BaseResponse<>(result);
        }
        catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    


     /*
      * 커스텀 코스 루틴 추가 API
      * [POST] /users/{userIdx}/custom/{customIdx}/add
      */
    @ResponseBody
    @PostMapping("/{customIdx}/add")
    public BaseResponse<PostCustomRes> addCustomRoutine(@PathVariable("userIdx") int userIdx, @PathVariable("customIdx") int customIdx, @RequestBody PostCustomRoutineReq postCustomRoutineReq){
        if(postCustomRoutineReq == null){
            return new BaseResponse<>(BaseResponseStatus.EMPTY_ROUTINE);
        }
        try{
            int userIdxByJwt = jwtService.getUserIdx();
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(BaseResponseStatus.INVALID_JWT);
            }

            int uId = customService.addCustomRoutine(userIdx, customIdx, postCustomRoutineReq);
            
            return new BaseResponse<>(new PostCustomRes(customIdx, uId));
        }
        catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }  


     /*
      * 커스텀 코스 루틴 빼기 API
      * [PATCH] /users/{userIdx}/custom/{customIdx}/remove
      */
    @ResponseBody
    @DeleteMapping("/{customIdx}/remove")
    public BaseResponse<DeleteCustomRemoveRoutineRes> removeCustomRoutine(@PathVariable("userIdx") int userIdx,@PathVariable("customIdx") int customIdx, @RequestBody DeleteCustomRemoveRoutineReq deleteCustomRemoveRoutineReq){
        if(deleteCustomRemoveRoutineReq.getCustomRoutineIdxs().size() < 1){
            return new BaseResponse<>(BaseResponseStatus.EMPTY_ROUTINE);
        }
        try{
            int userIdxByJwt = jwtService.getUserIdx();
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(BaseResponseStatus.INVALID_JWT);
            }
            customService.removeCustomRoutine(userIdx,customIdx,deleteCustomRemoveRoutineReq);
            
            return new BaseResponse<>(new DeleteCustomRemoveRoutineRes(userIdx,customIdx));
        }
        catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }


}

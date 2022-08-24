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

import java.util.ArrayList;
import java.util.List;

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
        if(postCustomReq.getExercises().size() < 1){
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
     * 커스텀 코스 운동 조회
     */
    @ResponseBody
    @GetMapping("/{customIdx}/routine/{routineIdx}")
    public BaseResponse<GetRoutineInfo> getRoutineExercise(@PathVariable("userIdx") int userIdx, @PathVariable("customIdx") int customIdx, @PathVariable("routineIdx") int routineIdx){
        
        try{
            int userIdxByJwt = jwtService.getUserIdx();
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(BaseResponseStatus.INVALID_JWT);
            }
            GetRoutineInfo getRoutineInfo = customProvider.getRoutineInfo(routineIdx,userIdx,customIdx);
            return new BaseResponse<>(getRoutineInfo);
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
     * 커스텀 코스 옵션 설정(변경) API
     * [Patch] /users/{userIdx}/custom/{customIdx}/routine/{routineIdx}
     */
    @ResponseBody
    @PatchMapping("/{customIdx}/option")
    public BaseResponse<String> setCustomOption(@PathVariable("userIdx") int userIdx, @PathVariable("customIdx") int customIdx, @RequestBody PatchCustomRoutineReq patchCustomRoutineReq){
        try{
            int userIdxByJwt = jwtService.getUserIdx();
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(BaseResponseStatus.INVALID_JWT);
            }
            customService.setCustomOption(userIdx, customIdx,patchCustomRoutineReq);
                //return new BaseResponse<>(BaseResponseStatus.MODIFY_FAIL_OPTION);
            
            return new BaseResponse<>("옵션 설정 성공");
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
    public BaseResponse<AddCustomRoutineRes> addCustomRoutine(@PathVariable("userIdx") int userIdx, @PathVariable("customIdx") int customIdx, @RequestBody PostCustomRoutineReq postCustomRoutineReq){
        if(postCustomRoutineReq == null){
            return new BaseResponse<>(BaseResponseStatus.EMPTY_ROUTINE);
        }
        try{
            int userIdxByJwt = jwtService.getUserIdx();
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(BaseResponseStatus.INVALID_JWT);
            }
            /*
            List<Integer> routineIdxs = new ArrayList<>();
            for(int i = 0 ; i < exercises.getExercises().size() ; i++){
                
                if(customService.addCustomRoutine(userIdx, customIdx, exercises.get(i))==0){
                    return new BaseResponse<>(BaseResponseStatus.ADD_FIAL_ROUTINE);
                }
                
                routineIdxs.add(customService.addCustomRoutine(userIdx, customIdx, exercises.getExercises().get(i)));
            }
            */
            int routineIdx = customService.addCustomRoutine(userIdx, customIdx, postCustomRoutineReq);
            //GetRoutineInfo routineInfo = customProvider.getRoutineInfo(routineIdx);
            return new BaseResponse<>(new AddCustomRoutineRes(routineIdx));
        }
        catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }  


     /*
      * 커스텀 코스 루틴 빼기 API
      * [DELETE] /users/{userIdx}/custom/{customIdx}/remove
      */
    @ResponseBody
    @DeleteMapping("/{customIdx}/remove")
    public BaseResponse<String> removeCustomRoutine(@PathVariable("userIdx") int userIdx,@PathVariable("customIdx") int customIdx, @RequestBody DeleteCustomRemoveRoutineReq deleteCustomRemoveRoutineReq){
        
        if(deleteCustomRemoveRoutineReq.getCustomRoutineIdxs().size() < 1){
            return new BaseResponse<>(BaseResponseStatus.EMPTY_ROUTINE);
        }
        
        try{
            
            int userIdxByJwt = jwtService.getUserIdx();
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(BaseResponseStatus.INVALID_JWT);
            }
            
            customService.removeCustomRoutine(userIdx,customIdx,deleteCustomRemoveRoutineReq);
            return new BaseResponse<>("루틴 삭제 성공");
            //return new BaseResponse<>(new DeleteCustomRemoveRoutineRes(userIdx,customIdx));
        }
        catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }


}
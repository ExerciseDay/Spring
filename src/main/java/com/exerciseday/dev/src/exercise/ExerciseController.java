package com.exerciseday.dev.src.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.exerciseday.dev.src.exercise.model.*;
import com.exerciseday.dev.utils.JwtService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
@RequestMapping("exercise")
public class ExerciseController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ExerciseProvider exerciseProvider;
    @Autowired
    private ExerciseService exerciseService;
    @Autowired
    private JwtService jwtService;

    public ExerciseController(ExerciseProvider exerciseProvider, ExerciseService exerciseService, JwtService jwtService){
        this.exerciseProvider = exerciseProvider;
        this.exerciseService = exerciseService;
        this.jwtService = jwtService;
        
    }

    /*
     * 운동 생성
     */
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostExerciseRes> createExercise(@RequestBody PostExerciseReq postExerciseReq){
        try{

            PostExerciseRes postExerciseRes = exerciseService.createExercise(postExerciseReq);
            return new BaseResponse<>(postExerciseRes);
        }
        catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /*
     * 운동 조회
     */
    @ResponseBody
    @GetMapping("/{exerciseIdx}")
    public BaseResponse<Exercise> getExercise(@PathVariable("exerciseIdx") int exerciseIdx){
        try{
            Exercise ex = exerciseProvider.getExercise(exerciseIdx);
            return new BaseResponse<>(ex);
        }
        catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    /*
     * 운동 삭제
     */
    @ResponseBody
    @DeleteMapping("/{exerciseIdx}")
    public BaseResponse<String> deleteExercise(@PathVariable("exerciseIdx") int exerciseIdx){
        try{

            exerciseService.deleteExercise(exerciseIdx);

            String result = "운동 삭제 성공";
            return new BaseResponse<>(result);
        }
        catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    /*
     * 운동 찜 API
     * [POST] /exercise/{exerciseIdx}/dibs?userIdx=
     */
    @ResponseBody
    @PostMapping("/{exerciseIdx}/dibs")
    public BaseResponse<String> postDibs(@PathVariable("exerciseIdx") int exerciseIdx,@RequestParam Integer userIdx){
        if(userIdx == null){
            return new BaseResponse<>(BaseResponseStatus.EMPTY_INDEX);
        }
        try{
            int userIdxByJwt = jwtService.getUserIdx();
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
            }
            exerciseService.postDibs(exerciseIdx,userIdx);
            String result = "운동 찜 성공";
            return new BaseResponse<>(result);
        }
        catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    /*
     * 운동 찜 목록 조회 API
     * [GET] /exercise/dibs?userIdx=?
     */
    @ResponseBody
    @GetMapping("/dibs")
    public BaseResponse<GetDibsRes> getDibs(@RequestParam Integer userIdx){
        if(userIdx == null){
            return new BaseResponse<>(BaseResponseStatus.EMPTY_INDEX);
        }
        try{
            int userIdxByJwt = jwtService.getUserIdx();
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
            }
            GetDibsRes getDibsRes = exerciseProvider.getDibs(userIdx);
            return new BaseResponse<>(getDibsRes);
            
        }
        catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }



    /*
     * 운동 찜 삭제 API
     * [DELETE] /exercise/{exerciseIdx}/dibs?userIdx=
     */
    @ResponseBody
    @DeleteMapping("/{exerciseIdx}/dibs")
    public BaseResponse<String> deleteDibs(@PathVariable("exerciseIdx") int exerciseIdx,@RequestParam Integer userIdx){
        if(userIdx == null){
            return new BaseResponse<>(BaseResponseStatus.EMPTY_INDEX);
        }
        try{
            int userIdxByJwt = jwtService.getUserIdx();
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
            }
            exerciseService.deleteDibs(exerciseIdx,userIdx);
            String result = "운동 찜 삭제 성공";
            return new BaseResponse<>(result);
        }
        catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    /*
     * 운동 검색 API
     * [GET] /exercise/search?what=
     */
    @ResponseBody
    @GetMapping("/search")
    public BaseResponse<GetExercisesRes> getExercises(@RequestParam(required = false) String what){
        if(what == null){
            return new BaseResponse<>(BaseResponseStatus.EMPTY_NAME);
        }
        try{
            
            return new BaseResponse<>(exerciseProvider.getExercises(what));
        }
        catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }
}

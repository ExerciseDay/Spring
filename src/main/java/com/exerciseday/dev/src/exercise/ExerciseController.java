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


    public ExerciseController(ExerciseProvider exerciseProvider, ExerciseService exerciseService){
        this.exerciseProvider = exerciseProvider;
        this.exerciseService = exerciseService;
        
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
            exerciseService.postDibs(exerciseIdx,userIdx);
            String result = "운동 찜 성공";
            return new BaseResponse<>(result);
        }
        catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }
}

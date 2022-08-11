package com.exerciseday.dev.src.exercise;

import org.springframework.stereotype.Service;

import com.exerciseday.dev.config.BaseException;
import com.exerciseday.dev.config.BaseResponseStatus;
import com.exerciseday.dev.src.exercise.model.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class ExerciseService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private ExerciseDao exerciseDao;
    private ExerciseProvider exerciseProvider;
    public ExerciseService(ExerciseDao exerciseDao, ExerciseProvider exerciseProvider){
        this.exerciseDao = exerciseDao;
        this.exerciseProvider = exerciseProvider;
    }


    public PostExerciseRes createExercise(PostExerciseReq postExerciseReq) throws BaseException{
        try{
            //


            int exerciseIdx = exerciseDao.createExercise(postExerciseReq);
            return new PostExerciseRes(exerciseIdx, postExerciseReq.getExerciseName());
        }
        catch(Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public void deleteExercise(int exerciseIdx) throws BaseException{
        try{
            if(exerciseProvider.checkExerciseExist(exerciseIdx)==0){
                throw new BaseException(BaseResponseStatus.EXIST_NO_EXERCISE);
            }
            if(exerciseDao.deleteExercise(exerciseIdx)==0){
                throw new BaseException(BaseResponseStatus.DELETE_FAIL_EXERCISE);
            }
        }
        catch(Exception e){
                throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
}

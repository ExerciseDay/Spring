package com.exerciseday.dev.src.exercise;

import org.springframework.stereotype.Service;

import com.exerciseday.dev.config.BaseException;
import com.exerciseday.dev.config.BaseResponseStatus;
import com.exerciseday.dev.src.exercise.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class ExerciseProvider {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private ExerciseDao exerciseDao;
    public ExerciseProvider(ExerciseDao exerciseDao){
        this.exerciseDao = exerciseDao;
    }

    public Exercise getExercise(int exerciseIdx) throws BaseException{
        try{

            Exercise ex = exerciseDao.getExercise(exerciseIdx);
            return ex;
        }
        catch(Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
    public GetExerciseTCRes getExerciseTC(int exerciseIdx) throws BaseException{
        try{
            GetExerciseTCRes exerciseTCRes = exerciseDao.getExerciseTCRes(exerciseIdx);
            return exerciseTCRes;
        }
        catch(Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
    public int checkExerciseExist(int exerciseIdx) throws BaseException{
        try{
            int result = exerciseDao.checkExerciseExist(exerciseIdx);
            return result;
        }
        catch(Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
}

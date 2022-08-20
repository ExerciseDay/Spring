package com.exerciseday.dev.src.exercise;

import org.springframework.stereotype.Service;

import com.exerciseday.dev.config.BaseException;
import com.exerciseday.dev.config.BaseResponseStatus;
import com.exerciseday.dev.src.exercise.model.*;

import java.util.List;

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
    /*
    public GetExercisesRes getExercises(String exerciseName) throws BaseException{
        
        try{
            System.out.println(exerciseName);
            String exNameSearchForm = "%" + exerciseName + "%";
            System.out.println(exNameSearchForm);
            return exerciseDao.getExercises(exNameSearchForm);
        }
        catch(Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
    */
    public GetExercisesRes getExercises(String exerciseName) throws BaseException{
        
        try{
            
            String exNameSearchForm = "%" + exerciseName + "%";
            int count = exerciseDao.getCount(exNameSearchForm);
            List<ExerciseInfo> exercises = exerciseDao.getExInfos(exNameSearchForm);
            return new GetExercisesRes(count, exercises);
        }
        catch(Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
    
    /*
    public int getCount(String exerciseName) throws BaseException{
        try{
            String exNameSearchForm = "%" + exerciseName + "%";
            System.out.println(exNameSearchForm);
            return exerciseDao.getCount(exNameSearchForm);
        }
        catch(Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public List<ExerciseInfo> getExInfo(String exerciseName) throws BaseException{
        try{
            String exNameSearchForm = "%" + exerciseName + "%";
            System.out.println(exNameSearchForm);
            return exerciseDao.getExInfos(exNameSearchForm);
        }
        catch(Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
    */

    public GetExerciseTCRes getExerciseTC(int exerciseIdx) throws BaseException{
        try{
            GetExerciseTCRes exerciseTCRes = exerciseDao.getExerciseTCRes(exerciseIdx);
            return exerciseTCRes;
        }
        catch(Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
    /*
    public GetDibsRes getDibs(int userIdx) throws BaseException{
        if(checkUserExist(userIdx)==0){
            throw new BaseException(BaseResponseStatus.EXIST_NO_USER);
        }
        try{
            return exerciseDao.getDibs(userIdx);
        }
        catch(Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
    */
    public int checkExerciseExist(int exerciseIdx) throws BaseException{
        try{
            int result = exerciseDao.checkExerciseExist(exerciseIdx);
            return result;
        }
        catch(Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
    public int checkUserExist(int userIdx) throws BaseException{
        try{
            int result = exerciseDao.checkUserExist(userIdx);
            return result;
        }
        catch(Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

}

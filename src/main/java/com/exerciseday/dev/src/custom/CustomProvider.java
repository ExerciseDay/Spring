package com.exerciseday.dev.src.custom;

import org.springframework.stereotype.Service;

import com.exerciseday.dev.config.BaseException;
import com.exerciseday.dev.config.BaseResponseStatus;
import com.exerciseday.dev.src.custom.model.CustomNTC;
import com.exerciseday.dev.src.custom.model.GetCustomRes;
import com.exerciseday.dev.src.custom.model.GetCustomRoutineInfoRes;
import com.exerciseday.dev.src.custom.model.GetExerciseTCRes;
import com.exerciseday.dev.src.custom.model.GetRoutineInfo;

import java.util.List;

import org.hibernate.jdbc.Expectations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class CustomProvider {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final CustomDao customDao;
    
    public CustomProvider(CustomDao customDao){
        this.customDao = customDao;
    }

    public GetCustomRes getCustom(int userIdx, int customIdx) throws BaseException{
        if(checkCustomExist(customIdx)==0){
            throw new BaseException(BaseResponseStatus.EXIST_NO_COURSE);
        }
        try{
            CustomNTC customNTC = customDao.getCustomNTC(userIdx,customIdx);
            List<GetCustomRoutineInfoRes> customRoutineInfos = customDao.getCustomRoutineInfos(userIdx, customIdx);
            GetCustomRes getCustomRes = new GetCustomRes(customNTC, customRoutineInfos);
            return getCustomRes;
        }
        catch(Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public GetExerciseTCRes getExerciseTC(int exerciseIdx) throws BaseException{
        try{
            GetExerciseTCRes exerciseTCRes = customDao.getExerciseTCRes(exerciseIdx);
            return exerciseTCRes;
        }
        catch(Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public GetRoutineInfo getRoutineInfo(int routineIdx, int userIdx, int customIdx) throws BaseException{
        try{
            return customDao.getRoutineInfo(routineIdx,userIdx,customIdx);
        }
        catch(Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public int checkExerciseExist(int exerciseIdx) throws BaseException{
        try{
            return customDao.checkExerciseExist(exerciseIdx);
        }
        catch(Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public int checkCustomExist(int customIdx) throws BaseException{
        try{
            return customDao.checkCustomExist(customIdx);
        }
        catch(Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public int checkCustomRoutineExist(int customRoutineIdx) throws BaseException{
        try{
            return customDao.checkCustomRoutineExist(customRoutineIdx);
        }
        catch(Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public int checkUserHasCustom(int userIdx, int customIdx) throws BaseException{
        try{
            return customDao.checkUserHasCustom(userIdx, customIdx);
        }
        catch(Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public int checkCustomHasRoutine(int customIdx, int customRoutineIdx) throws BaseException{
        try{
            return customDao.checkCustomHasRoutine(customIdx,customRoutineIdx);
        }
        catch(Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
}

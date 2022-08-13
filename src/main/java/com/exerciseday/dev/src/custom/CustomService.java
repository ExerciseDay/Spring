package com.exerciseday.dev.src.custom;

import org.springframework.stereotype.Service;

import com.exerciseday.dev.config.BaseException;
import com.exerciseday.dev.config.BaseResponseStatus;
import com.exerciseday.dev.src.custom.model.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class CustomService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final CustomDao customDao;
    private final CustomProvider customProvider;
    
    public CustomService(CustomDao customDao,CustomProvider customProvider){
        this.customDao = customDao;
        this.customProvider = customProvider;
    }

    public int createCustom(int userIdx, PostCustomReq postCustomReq) throws BaseException{
        
        for(int i = 0 ; i < postCustomReq.getCustomRoutines().size() ; i++){
            
            if(customProvider.checkExerciseExist(postCustomReq.getCustomRoutines().get(i).getExerciseIdx())==0){
                
                throw new BaseException(BaseResponseStatus.EXIST_NO_EXERCISE);
            }
        }
        int times = 0;
        int calories = 0;
        try{
            
            int customIdx = customDao.createCustom(userIdx, postCustomReq.getCustomName(),postCustomReq.getCustomIntroduce());
            
            for(int i = 0 ; i < postCustomReq.getCustomRoutines().size() ; i++){
                PostCustomRoutineReq postCustomRoutineReq = postCustomReq.getCustomRoutines().get(i);
                
                customDao.createCustomRoutine(userIdx,customIdx, postCustomRoutineReq);          
                
                GetExerciseTCRes etc = customProvider.getExerciseTC(postCustomRoutineReq.getExerciseIdx());          
                
                //times = rep * set * exTime + set * rest
                times+= postCustomRoutineReq.getRep() * postCustomRoutineReq.getSet() * etc.getExTime() + postCustomRoutineReq.getSet() * postCustomRoutineReq.getRest();
                //calories = rep * set * exCalory
                calories = postCustomRoutineReq.getRep() * postCustomRoutineReq.getSet() * etc.getExCalory();
            }

            customDao.addCustomTC(customIdx,times,calories);
            
            return customIdx;
        } catch(Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public int addCustomRoutine(int userIdx, int customIdx, PostCustomRoutineReq postCustomRoutineReq) throws BaseException{
        if(customProvider.checkCustomExist(customIdx)==0){
            throw new BaseException(BaseResponseStatus.EXIST_NO_COURSE);
        }
        if(customProvider.checkExerciseExist(postCustomRoutineReq.getExerciseIdx())==0){
            throw new BaseException(BaseResponseStatus.EXIST_NO_EXERCISE);
        }
        
        try{
            customDao.createCustomRoutine(userIdx, customIdx, postCustomRoutineReq);
                
            return userIdx;
        }
        catch(Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public void deleteCustom(int userIdx, int customIdx) throws BaseException{
        if(customProvider.checkCustomExist(customIdx)==0){
            throw new BaseException(BaseResponseStatus.EXIST_NO_COURSE);
        }
        //유저의 커스텀 코스가 맞는가?!

        
        try{
            if(customDao.deleteCustom(customIdx)==0){
                throw new BaseException(BaseResponseStatus.DELETE_FAIL_COURSE);
            }
        }
        catch(Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public void removeCustomRoutine(int userIdx,int customIdx,DeleteCustomRemoveRoutineReq deleteCustomRemoveRoutineReq) throws BaseException{
        if(customProvider.checkCustomExist(customIdx)==0){
            throw new BaseException(BaseResponseStatus.EXIST_NO_COURSE);
        }
        int max = deleteCustomRemoveRoutineReq.getCustomRoutineIdxs().size();
        for(int i = 0 ; i < max ; i++){
            if(customProvider.checkCustomRoutineExist(deleteCustomRemoveRoutineReq.getCustomRoutineIdxs().get(i))==0){
                throw new BaseException(BaseResponseStatus.EXIST_NO_ROUTINE);
            }
        }

        try{
            for(int i = 0 ; i < max ; i++){
                if(customDao.removeCustomRoutine(userIdx,customIdx,deleteCustomRemoveRoutineReq.getCustomRoutineIdxs().get(i))==0){
                    throw new BaseException(BaseResponseStatus.DELETE_FAIL_ROUTINE);
                }
            }

        }
        catch(Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
}

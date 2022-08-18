package com.exerciseday.dev.src.custom;

import org.springframework.stereotype.Service;

import com.exerciseday.dev.config.BaseException;
import com.exerciseday.dev.config.BaseResponse;
import com.exerciseday.dev.config.BaseResponseStatus;
import com.exerciseday.dev.src.custom.model.*;

import java.util.List;

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
        
        for(int i = 0 ; i < postCustomReq.getExercises().size() ; i++){
            
            if(customProvider.checkExerciseExist(postCustomReq.getExercises().get(i).getExerciseIdx())==0){
                
                throw new BaseException(BaseResponseStatus.EXIST_NO_EXERCISE);
            }
        }
        int times = 0;
        int calories = 0;
        int rest = 90;
        try{
            
            int customIdx = customDao.createCustom(userIdx, postCustomReq.getCustomName(),postCustomReq.getCustomIntroduce());
            
            for(int i = 0 ; i < postCustomReq.getExercises().size() ; i++){
                PostCustomRoutineReq postCustomRoutineReq = postCustomReq.getExercises().get(i);
                
                if(customDao.createCustomRoutine(userIdx,customIdx, postCustomRoutineReq)==0){
                    throw new BaseException(BaseResponseStatus.ADD_FIAL_ROUTINE);
                }          
                
                GetExerciseTCRes etc = customProvider.getExerciseTC(postCustomRoutineReq.getExerciseIdx());          
                
                //times = rep * set * exTime + set * rest
                times+= postCustomRoutineReq.getRep() * postCustomRoutineReq.getSet() * etc.getExTime() + postCustomRoutineReq.getSet() * rest;
                //calories = rep * set * exCalory
                calories = postCustomRoutineReq.getRep() * postCustomRoutineReq.getSet() * etc.getExCalory();
            }

            return customDao.addCustomTC(customIdx,times,calories);
            
            //return customIdx;
        } catch(Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public int addCustomRoutine(int userIdx, int customIdx, PostCustomRoutineReq postCustomRoutineReq) throws BaseException{
        if(customProvider.checkCustomExist(customIdx)==0){
            throw new BaseException(BaseResponseStatus.EXIST_NO_COURSE);
        }
        /*
        for(int i = 0 ; i < postCustomRoutineReq.getExerciseIdx().size() ; i++){
            if(customProvider.checkExerciseExist(postCustomRoutineReq.getExerciseIdx().get(i))==0){
                throw new BaseException(BaseResponseStatus.EXIST_NO_EXERCISE);
            }
        }
        */
        if(customProvider.checkExerciseExist(postCustomRoutineReq.getExerciseIdx())==0){
            throw new BaseException(BaseResponseStatus.EXIST_NO_EXERCISE);
        }
        if(customProvider.checkUserHasCustom(userIdx, customIdx)==0){
            throw new BaseException(BaseResponseStatus.ADD_FIAL_ROUTINE);
        }
        
        try{
            /*
            for(int i = 0 ; i < postCustomRoutineReq.getExerciseIdx().get(i) ; i++){
                if(customDao.createCustomRoutine(userIdx, customIdx, postCustomRoutineReq.getExerciseIdx().get(i))==0){
                    throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
                }
            }
            */

            if(customDao.createCustomRoutine(userIdx,customIdx, postCustomRoutineReq)==0){
                throw new BaseException(BaseResponseStatus.ADD_FIAL_ROUTINE);
            }   
            
            return autoCustomTC(userIdx, customIdx);
            //times = rep * set * exTime + set * rest
            //times+= postCustomRoutineReq.getRep() * postCustomRoutineReq.getSet() * etc.getExTime() + postCustomRoutineReq.getSet() * rest;
            //calories = rep * set * exCalory
            //calories = postCustomRoutineReq.getRep() * postCustomRoutineReq.getSet() * etc.getExCalory();
            
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
        if(customProvider.checkUserHasCustom(userIdx, customIdx)==0){
            throw new BaseException(BaseResponseStatus.DELETE_FAIL_COURSE);
        }
        
        try{
            if(customDao.deleteCustom(customIdx)==0){
                throw new BaseException(BaseResponseStatus.DELETE_FAIL_COURSE);
            }
        }
        catch(Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public int setCustomOption(int userIdx,int customIdx, PatchCustomRoutineReq patchCustomRoutineReq) throws BaseException{
        
        if(customProvider.checkCustomExist(customIdx)==0){
            throw new BaseException(BaseResponseStatus.EXIST_NO_COURSE);
        }
        if(customProvider.checkCustomRoutineExist(patchCustomRoutineReq.getCustomRoutineIdx())==0){
            throw new BaseException(BaseResponseStatus.EXIST_NO_ROUTINE);
        }
        if(customProvider.checkUserHasCustom(userIdx,customIdx)==0){
            throw new BaseException(BaseResponseStatus.INVALID_RELATION);
        }
        if(customProvider.checkCustomHasRoutine(customIdx,patchCustomRoutineReq.getCustomRoutineIdx())==0){
            throw new BaseException(BaseResponseStatus.INVALID_RELATION);
        }
        try{
            
            if(customDao.setCustomOption(patchCustomRoutineReq)==0){
                throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
            }
            return autoCustomTC(userIdx, customIdx);

        }
        catch(Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public int autoCustomTC(int userIdx, int customIdx) throws BaseException{
        if(customProvider.checkCustomExist(customIdx)==0){
            throw new BaseException(BaseResponseStatus.EXIST_NO_COURSE);
        }
        //유저의 커스텀 코스가 맞는가?!
        if(customProvider.checkUserHasCustom(userIdx, customIdx)==0){
            throw new BaseException(BaseResponseStatus.DELETE_FAIL_COURSE);
        }

        try{
            int times = 0;
            int calories = 0;
            int rest = 90;
            List<PostCustomRoutineReq> infos = customDao.getCustomTCList(userIdx, customIdx);

            for(int i = 0 ; i < infos.size() ; i++){
                GetExerciseTCRes etc = customProvider.getExerciseTC(infos.get(i).getExerciseIdx());          
                //times = rep * set * exTime + set * rest
                times+= infos.get(i).getRep() * infos.get(i).getSet() * etc.getExTime() + infos.get(i).getSet() * rest;
                //calories = rep * set * exCalory
                calories = infos.get(i).getRep() * infos.get(i).getSet() * etc.getExCalory();
            }
            return customDao.addCustomTC(customIdx, times, calories);

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
            if(autoCustomTC(userIdx, customIdx)==0){
                throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
            }

        }
        catch(Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
}

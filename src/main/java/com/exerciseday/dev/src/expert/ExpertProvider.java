package com.exerciseday.dev.src.expert;

import org.springframework.stereotype.Service;

import com.exerciseday.dev.config.BaseException;
import com.exerciseday.dev.config.BaseResponseStatus;
import com.exerciseday.dev.src.expert.model.*;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class ExpertProvider {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private ExpertDao expertDao;
    public ExpertProvider(ExpertDao expertDao){
        this.expertDao = expertDao;
    }

    public int checkTrainerExist(int trainerIdx) throws BaseException{
        try{
            return expertDao.checkTrainerExist(trainerIdx);
        } catch(Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public int checkExerciseExist(int exerciseIdx) throws BaseException{
        try{
            return expertDao.checkExerciseExist(exerciseIdx);
        }
        catch(Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public int checkExpertExist(int expertIdx) throws BaseException{
        try{
            return expertDao.checkExpertExist(expertIdx);
        }
        catch(Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public GetExpertRes getExpert(int expertIdx) throws BaseException{
        try{
            if(checkExpertExist(expertIdx)==0){
                throw new BaseException(BaseResponseStatus.EXIST_NO_EXPERTCOURSE);
            }

            
            ExpertNTC expert = expertDao.getExpertNTC(expertIdx);
            List<GetExpertRoutineInfoRes> expertRoutineInfos = expertDao.getExpertRoutineInfos(expertIdx);
            GetExpertRes expertRes = new GetExpertRes(expertIdx, expert, expertRoutineInfos);
            return expertRes;
        }
        catch(Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public List<ExpertByPart> getExpertsByPart(GetExpertByPartReq getExpertByPartReq) throws BaseException{
        try{
            List<ExpertByPart> expertList = expertDao.getExpertsByPart(getExpertByPartReq);
            return expertList;

        } catch(Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    
}

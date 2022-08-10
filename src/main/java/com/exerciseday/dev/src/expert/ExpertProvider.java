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

    public List<ExpertByPart> getExpertsByPart(GetExpertByPartReq getExpertByPartReq) throws BaseException{
        try{
            List<ExpertByPart> expertList = expertDao.getExpertsByPart(getExpertByPartReq);
            return expertList;

        } catch(Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
}

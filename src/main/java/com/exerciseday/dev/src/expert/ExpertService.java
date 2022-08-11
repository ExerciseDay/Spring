package com.exerciseday.dev.src.expert;

import org.springframework.stereotype.Service;

import com.exerciseday.dev.config.BaseException;
import com.exerciseday.dev.config.BaseResponseStatus;
import com.exerciseday.dev.src.expert.model.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class ExpertService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private ExpertDao expertDao;
    private ExpertProvider expertProvider;
    public ExpertService(ExpertDao expertDao, ExpertProvider expertProvider){
        this.expertDao = expertDao;
        this.expertProvider = expertProvider;
    }

    public int createExpert(PostExpertReq postExpertReq) throws BaseException{
        try{
            if(expertProvider.checkTrainerExist(postExpertReq.getTrainerIdx())==0){
                throw new BaseException(BaseResponseStatus.EXIST_NO_TRAINER);
            }
            for(int i = 0 ; i < postExpertReq.getExpertRoutines().size() ; i++){
                if(expertProvider.checkExerciseExist(postExpertReq.getExpertRoutines().get(i).getExerciseIdx())==0){
                    throw new BaseException(BaseResponseStatus.EXIST_NO_EXERCISE);
                }
            }
            int expertIdx = expertDao.createExpert(postExpertReq.getTrainerIdx(),postExpertReq.getExpertName(),postExpertReq.getExpertPart(),postExpertReq.getExpertDetailPart());
            for(int i = 0 ; i < postExpertReq.getExpertRoutines().size() ; i++){
                expertDao.createExpertRoutine(expertIdx, postExpertReq.getExpertRoutines().get(i));
            }
            return expertIdx;
        } catch(Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
    
}

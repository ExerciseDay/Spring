package com.exerciseday.dev.src.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exerciseday.dev.config.BaseException;
import com.exerciseday.dev.config.BaseResponseStatus;
import com.exerciseday.dev.src.user.model.GetUserRes;
import com.exerciseday.dev.utils.JwtService;

import static com.exerciseday.dev.config.BaseResponseStatus.DATABASE_ERROR;
import static com.exerciseday.dev.config.BaseResponseStatus.EXIST_NO_PHONE;;

@Service
public class UserProvider {

    private final UserDao userDao;
    private final JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    //@Autowired
    public UserProvider(UserDao userDao, JwtService jwtService){
        this.userDao = userDao;
        this.jwtService = jwtService;
    }

    public GetUserRes getUserByEmail(String Email) throws BaseException{
        try {
            GetUserRes getUserRes = userDao.getUserByEmail(Email);
            return getUserRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetUserRes getUserByIdx(int userIdx) throws BaseException{
        try {
            GetUserRes getUserRes = userDao.getUserByIdx(userIdx);
            return getUserRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        } 
    }
    
    public GetUserRes getUserByPhone(String phone) throws BaseException{
        
        if(checkPhoneExist(phone)==0){
            throw new BaseException(EXIST_NO_PHONE);
        }

        try {

            GetUserRes getUserRes = userDao.getUserByPhone(phone);
            return getUserRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        } 
    }

    public int checkEmail(String Email) throws BaseException{
        try{                
            return userDao.checkEmail(Email);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkNicknameExist(String nickname) throws BaseException{
        try{
            
            return userDao.checkNicknameExist(nickname);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkUserExist(int userIdx) throws BaseException{
        try{
            
            return userDao.checkUserExist(userIdx);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkPhoneExist(String phone) throws BaseException{
        try{
            
            return userDao.checkPhoneExist(phone);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}

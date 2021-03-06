package com.exerciseday.dev.src.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exerciseday.dev.config.BaseException;
import com.exerciseday.dev.src.user.model.GetUserRes;
import com.exerciseday.dev.utils.JwtService;

import static com.exerciseday.dev.config.BaseResponseStatus.DATABASE_ERROR;

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
    
    public int checkEmail(String Email) throws BaseException{
        try{                
            return userDao.checkEmail(Email);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkNickNameExist(String nickName) throws BaseException{
        try{
            
            return userDao.checkNickNameExist(nickName);
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
}

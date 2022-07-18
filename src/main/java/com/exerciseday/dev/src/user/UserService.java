package com.exerciseday.dev.src.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.exerciseday.dev.src.user.model.PostUserRes;
import com.exerciseday.dev.src.user.model.PostUserReq;
import com.exerciseday.dev.utils.JwtService;

import com.exerciseday.dev.config.BaseException;

import static com.exerciseday.dev.config.BaseResponseStatus.*;
import com.exerciseday.dev.utils.SHA256;

@Service
public class UserService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserDao userDao;
    private final UserProvider userProvider;
    private final JwtService jwtService;

    //@Autowired
    public UserService(UserDao userDao, UserProvider userProvider, JwtService jwtService){
        this.userDao = userDao;
        this.userProvider = userProvider;
        this.jwtService = jwtService;
    }


    public PostUserRes createUser(PostUserReq postUserReq) throws BaseException{
        //이메일 중복
        if(userProvider.checkEmail(postUserReq.getEmail())==1){
            throw new BaseException(POST_USERS_EXISTS_EMAIL);
        }
        //닉네임 중복
        if(userProvider.checkNickNameExist(postUserReq.getNickName())==1){
            throw new BaseException(POST_USERS_EXISTS_NICKNAME);
        }
        //암호화
        String pwd;
        try{
            new SHA256();            
            pwd = SHA256.encrypt(postUserReq.getPassword());  
            postUserReq.setPassword(pwd);
        } catch (Exception exception) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }
        try{
            int userIdx = userDao.createUser(postUserReq);
            //jwt 발급.
            String jwt = jwtService.createJwt(userIdx);
            return new PostUserRes(jwt,userIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}

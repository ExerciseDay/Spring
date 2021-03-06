package com.exerciseday.dev.src.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exerciseday.dev.config.BaseException;
import com.exerciseday.dev.config.BaseResponse;
import com.exerciseday.dev.src.auth.model.PostLoginReq;
import com.exerciseday.dev.src.auth.model.PostLoginRes;
import com.exerciseday.dev.src.auth.model.User;
import com.exerciseday.dev.utils.JwtService;
import com.exerciseday.dev.utils.SHA256;

import static com.exerciseday.dev.config.BaseResponseStatus.*;

@Service
public class AuthService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final AuthDao authDao;
    private final AuthProvider authProvider;
    private final JwtService jwtService;


    //@Autowired
    public AuthService(AuthDao authDao, AuthProvider authProvider, JwtService jwtService) {
        this.authDao = authDao;
        this.authProvider = authProvider;
        this.jwtService = jwtService;

    }


    public PostLoginRes login(PostLoginReq postLoginReq) throws BaseException {
        User user = authDao.getPwd(postLoginReq);
        String encryptPwd;
        try {
            new SHA256();
            encryptPwd = SHA256.encrypt(postLoginReq.getPwd());

        }
        catch (Exception exception) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }

        if(user.getPassword().equals(encryptPwd)){

            int userIdx = user.getUserIdx();
            String jwt = jwtService.createJwt(userIdx);
            return new PostLoginRes(userIdx, jwt);
        }
        else{
            throw new BaseException(FAILED_TO_LOGIN);
        }
    }
}
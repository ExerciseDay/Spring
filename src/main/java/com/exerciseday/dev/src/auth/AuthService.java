package com.exerciseday.dev.src.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exerciseday.dev.config.BaseException;
import com.exerciseday.dev.config.BaseResponse;
import com.exerciseday.dev.config.BaseResponseStatus;
import com.exerciseday.dev.src.auth.model.GetExerciseRes;
import com.exerciseday.dev.src.auth.model.GetTagRes;
import com.exerciseday.dev.src.auth.model.PostLoginReq;
import com.exerciseday.dev.src.auth.model.PostLoginRes;
import com.exerciseday.dev.src.auth.model.User;
import com.exerciseday.dev.utils.JwtService;
import com.exerciseday.dev.utils.SHA256;

import static com.exerciseday.dev.config.BaseResponseStatus.*;

import java.util.List;

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
        // 유저 가입 여부 검사
        if(authProvider.checkEmail(postLoginReq.getEmail())==0){
            throw new BaseException(BaseResponseStatus.EXIST_NO_USER);
        }
        //탈퇴한 유저인가?
        if(authProvider.checkUserDeleteByEmail(postLoginReq.getEmail())==1){
            throw new BaseException(DELETED_USER);
        }
        //이미 로그인한 유저인가?
        
        User user = authDao.getUserByLoginReq(postLoginReq);
        String encryptPwd;
        try {
            new SHA256();
            encryptPwd = SHA256.encrypt(postLoginReq.getPassword());
            postLoginReq.setPassword(encryptPwd);
        }
        catch (Exception exception) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }
        
        if(user.getPassword().equals(encryptPwd)){
            
            int userIdx = user.getUserIdx();
            String jwt = jwtService.createJwt(userIdx);
            GetExerciseRes exs = authDao.getRandomEx();
            List<GetTagRes> tags = authProvider.getRandomTags();
            logger.info("[POST] /auth/login 로그인 성공 ###############");
            
            return new PostLoginRes(userIdx, jwt,user.getNickname(),user.getUserImg(),user.getUserGoal(),exs,tags);
        } else{            
            throw new BaseException(FAILED_TO_LOGIN);
        }
    }

    public void logout(int userIdx) throws BaseException{
        if(authProvider.checkUserExist(userIdx)==0){
            throw new BaseException(EXIST_NO_USER);
        }
        if(authProvider.checkUserDelete(userIdx)==1){
            throw new BaseException(DELETED_USER);
        }
        if(authProvider.checkUserLogout(userIdx)==1){
            throw new BaseException(LOGOUT_USER);
        }
        try{
            authDao.logout(userIdx);
        }
        catch(Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
        
    }
}

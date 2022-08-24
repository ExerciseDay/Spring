package com.exerciseday.dev.src.auth;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.exerciseday.dev.config.BaseException;
import com.exerciseday.dev.config.BaseResponseStatus;
import com.exerciseday.dev.src.auth.model.GetExerciseRes;
import com.exerciseday.dev.src.auth.model.GetTagRes;
import com.exerciseday.dev.src.auth.model.PostLoginReq;
import com.exerciseday.dev.src.auth.model.PostLoginRes;
import com.exerciseday.dev.src.auth.model.User;
import com.exerciseday.dev.utils.JwtService;


@Service
public class AuthProvider {
    private final AuthDao authDao;
    private final JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    public AuthProvider(AuthDao authDao, JwtService jwtService){
        this.authDao = authDao;
        this.jwtService = jwtService;
    }

    public int checkEmail(String email) throws BaseException{
        try{                
            return authDao.checkEmail(email);
        } catch (Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
    public int checkUserExist(int userIdx) throws BaseException{
        try{                
            return authDao.checkUserExist(userIdx);
        } catch (Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
    public int getUserIdxByJWT(int userIdxByJwt) throws BaseException{
        if(checkUserExist(userIdxByJwt)==0){
            throw new BaseException(BaseResponseStatus.EXIST_NO_USER);
        }
        try{      

            return authDao.getUserIdxByJWT(userIdxByJwt);
        } catch (Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public int checkUserLogin(int userIdx) throws BaseException{
        try{
            return authDao.checkUserLogin(userIdx);
        }
        catch(Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
    public int checkUserLogout(int userIdx) throws BaseException{
        try{
            return authDao.checkUserLogout(userIdx);
        }
        catch(Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
    public int checkUserDelete(int userIdx) throws BaseException{
        try{
            return authDao.checkUserDelete(userIdx);
        }
        catch(Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
    public int checkUserDeleteByEmail(String email) throws BaseException{
        try{
            return authDao.checkUserDeleteByEmail(email);
        }
        catch(Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
    public List<GetTagRes> getRandomTags() throws BaseException{
        try{
            return authDao.getRamdomTags();
        }
        catch(Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
    
    public PostLoginRes autoLogin(int userIdx,String jwt) throws BaseException{
        if(checkUserExist(userIdx)==0){
            throw new BaseException(BaseResponseStatus.EXIST_NO_USER);
        }
        if(checkUserDelete(userIdx)==1){
            throw new BaseException(BaseResponseStatus.DELETED_USER);
        }
        try{


            User user = authDao.autoLogin(userIdx);
            GetExerciseRes exs = authDao.getRandomEx();
            List<GetTagRes> tags = getRandomTags();
            return new PostLoginRes(userIdx, jwt, user.getNickname(), user.getUserImg(), user.getUserGoal(),exs, tags);
        }
        catch(Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
}

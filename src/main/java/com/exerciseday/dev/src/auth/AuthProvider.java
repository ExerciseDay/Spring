package com.exerciseday.dev.src.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.exerciseday.dev.config.BaseException;
import com.exerciseday.dev.config.BaseResponseStatus;
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
        try{      
            if(checkUserExist(userIdxByJwt)==0){
                throw new BaseException(BaseResponseStatus.EXIST_NO_USER);
            }
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
}

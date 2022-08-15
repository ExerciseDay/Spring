package com.exerciseday.dev.src.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exerciseday.dev.config.BaseException;
import com.exerciseday.dev.config.BaseResponseStatus;
import com.exerciseday.dev.src.user.model.*;
import com.exerciseday.dev.utils.JwtService;

import static com.exerciseday.dev.config.BaseResponseStatus.DATABASE_ERROR;
import static com.exerciseday.dev.config.BaseResponseStatus.EXIST_NO_PHONE;

import java.util.ArrayList;
import java.util.List;;

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

    public User getUser(int userIdx) throws BaseException{
        try {
            User user = userDao.getUser(userIdx);
            return user;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        } 
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

    public GetUserFindEmailRes getUserFindEmail(String phone) throws BaseException{
        
        if(checkPhoneExist(phone)==0){
            throw new BaseException(EXIST_NO_PHONE);
        }

        try {

            GetUserFindEmailRes getUserFindEmailRes = userDao.getUserFindEmail(phone);
            return getUserFindEmailRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        } 
    }


    public PostUserFindPwdRes postUserFindPwd(PostUserFindPwdReq postUserFindPwdReq) throws BaseException{
        
        if(checkEmail(postUserFindPwdReq.getEmail())==0){
            throw new BaseException(BaseResponseStatus.EXIST_NO_EMAIL);
        }

        if(checkPhoneExist(postUserFindPwdReq.getPhone())==0){
            throw new BaseException(EXIST_NO_PHONE);
        }

        // 입력한 이메일과 전화번호는 같은 유저의 정보인가?
        GetUserRes getUserRes1 = userDao.getUserByEmail(postUserFindPwdReq.getEmail());
        GetUserRes getUserRes2 = userDao.getUserByPhone(postUserFindPwdReq.getPhone());
        if(getUserRes1.getUserIdx() != getUserRes2.getUserIdx()){
            throw new BaseException(BaseResponseStatus.DIFFERENT_USERS);
        }

        try {
            int userIdx = userDao.getUserByPhone(postUserFindPwdReq.getPhone()).getUserIdx();
            String jwt = jwtService.createJwt(userIdx);
            PostUserFindPwdRes postUserFindPwdRes = new PostUserFindPwdRes(userIdx,jwt);
            return postUserFindPwdRes;
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

    public int checkExpertExist(int expertIdx) throws BaseException{
        try{
            return userDao.checkExpertExist(expertIdx);
        }
        catch(Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public int checkUserExpertExist(int userIdx, int expertIdx) throws BaseException{
        try{
            return userDao.checkUserExpertExist(userIdx,expertIdx);
        }
        catch(Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }


    public GetUserCourseRes getUserCourse(int userIdx) throws BaseException{        
        if(checkUserExist(userIdx)==0){
            throw new BaseException(BaseResponseStatus.EXIST_NO_USER);
        }
        try{
            User user = userDao.getUser(userIdx);

            List<GetUserCustomRes> getUserCustoms = userDao.getUserCustoms(userIdx);

            List<Integer> list = userDao.getRelation(userIdx);
            
            List<GetUserExpertRes> getUserExperts = new ArrayList<>();

            for(int i = 0 ; i < list.size() ; i++){
                getUserExperts.add(userDao.getUserExpert(list.get(i)));
            }
             
            GetUserCourseRes getUserCourseRes = new GetUserCourseRes(user.getUserIdx(),user.getNickname(),user.getImg(),user.getGoal(),getUserCustoms,getUserExperts);
            return getUserCourseRes;
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetExerciseTCRes getExerciseTC(int exerciseIdx) throws BaseException{
        try{
            GetExerciseTCRes exerciseTCRes = userDao.getExerciseTCRes(exerciseIdx);
            return exerciseTCRes;
        }
        catch(Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
    public int checkExerciseExist(int exerciseIdx) throws BaseException{
        try{
            int result = userDao.checkExerciseExist(exerciseIdx);
            return result;
        }
        catch(Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
    public int checkUserLogin(int userIdx) throws BaseException{
        try{
            return userDao.checkUserLogin(userIdx);
        }
        catch(Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public int checkUserLogout(int userIdx) throws BaseException{
        try{
            return userDao.checkUserLogout(userIdx);
        }
        catch(Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public int checkUserDelete(int userIdx) throws BaseException{
        try{
            return userDao.checkUserDelete(userIdx);
        }
        catch(Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}

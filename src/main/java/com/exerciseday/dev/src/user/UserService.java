package com.exerciseday.dev.src.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.exerciseday.dev.src.user.model.PostUserRes;
import com.exerciseday.dev.src.user.model.PatchUserEditImgReq;
import com.exerciseday.dev.src.user.model.PatchUserEditNicknameReq;
import com.exerciseday.dev.src.user.model.PatchUserEditPwdReq;
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
            throw new BaseException(DUPLICATED_EMAIL);
        }
        //닉네임 중복
        if(userProvider.checkNicknameExist(postUserReq.getNickname())==1){
            throw new BaseException(DUPLICATED_NICKNAME);
        }
        //전화번호 중복
        if(userProvider.checkPhoneExist(postUserReq.getPhone())==1){
            throw new BaseException(DUPLICATED_PHONE);
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
            logger.info("[POST] /users 회원가입 성공 #############");
            return new PostUserRes(userIdx,jwt);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void editUserPwd(PatchUserEditPwdReq patchUserEditPwdReq) throws BaseException {
        if(userProvider.checkUserExist(patchUserEditPwdReq.getUserIdx())==0){
            throw new BaseException(EXIST_NO_USER);
        }
        
        //암호화
        String oldPwd = userProvider.getUser(patchUserEditPwdReq.getUserIdx()).getPassword();
        String pwd;        
        try{
            new SHA256();  
            pwd = SHA256.encrypt(patchUserEditPwdReq.getPassword());  
            patchUserEditPwdReq.setPassword(pwd);
        } catch (Exception exception) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }
        // 이전 비밀번호와 다른 비밀번호인가?  
        if(oldPwd.equals(pwd)){
            throw new BaseException(DIFFERENT_PASSWORD);
        }

        try{  

 
            int result = userDao.editPwd(patchUserEditPwdReq);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_PASSWORD);
            }
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void editUserNickname(PatchUserEditNicknameReq patchUserEditNicknameReq) throws BaseException {
        //존재하는 유저?
        if(userProvider.checkUserExist(patchUserEditNicknameReq.getUserIdx())==0){
            throw new BaseException(EXIST_NO_USER);
        }       
        
        // 이미 존재하는 닉네임?
        if(userProvider.checkNicknameExist(patchUserEditNicknameReq.getNickname())==1){
            throw new BaseException(DUPLICATED_NICKNAME);
        }

        try{   
            int result = userDao.editNickname(patchUserEditNicknameReq);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_NICKNAME);
            }
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void editUserImg(PatchUserEditImgReq patchUserEditImgReq) throws BaseException {
        //존재하는 유저?
        if(userProvider.checkUserExist(patchUserEditImgReq.getUserIdx())==0){
            throw new BaseException(EXIST_NO_USER);
        }       


        try{   
            int result = userDao.editImg(patchUserEditImgReq);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_IMG);
            }
            
            
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
    
    public void deleteUser(int userIdx) throws BaseException {
        //존재하는 유저?
        if(userProvider.checkUserExist(userIdx)==0){
            throw new BaseException(EXIST_NO_USER);
        }       


        try{   
            int result = userDao.deleteUser(userIdx);
            if(result == 0){
                throw new BaseException(DELETE_FAIL_USER);
            }
            
            
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

}

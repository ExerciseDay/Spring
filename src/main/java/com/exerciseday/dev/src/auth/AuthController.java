package com.exerciseday.dev.src.auth;



import static com.exerciseday.dev.config.BaseResponseStatus.*;
import static com.exerciseday.dev.utils.ValidationRegex.isRegexEmail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.exerciseday.dev.config.BaseException;
import com.exerciseday.dev.config.BaseResponse;
import com.exerciseday.dev.config.BaseResponseStatus;
import com.exerciseday.dev.src.auth.model.PostLoginReq;
import com.exerciseday.dev.src.auth.model.PostLoginRes;
import com.exerciseday.dev.utils.JwtService;


@RestController
@RequestMapping("/auth")
public class AuthController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final AuthProvider authProvider;
    @Autowired
    private final AuthService authService;
    @Autowired
    private final JwtService jwtService;

    public AuthController(AuthProvider authProvider, AuthService authService, JwtService jwtService){
        this.authProvider = authProvider;
        this.authService = authService;
        this.jwtService = jwtService;
    }
    /**
     * jwt 로그인 API
     * [POST] /auth/login
     * @return BaseResponse<>(PostLoginRes)
     */
    @ResponseBody
    @PostMapping("/login")
    public BaseResponse<PostLoginRes> login(@RequestBody PostLoginReq postLoginReq){ 
        try{
            //이메일 입력 x
            if(postLoginReq.getEmail() == null)
            {
                return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
            }

            //이메일 형식 
            if(!isRegexEmail(postLoginReq.getEmail()))
            {
                return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
            }

            //비밀번호 입력 x        
            if(postLoginReq.getPassword() == null)
            {
                return new BaseResponse<>(POST_USERS_EMPTY_PASSWORD);
            }
            logger.info("[POST] /auth/login 로그인 API 호출 성공######################");
            PostLoginRes postLoginRes = authService.login(postLoginReq);
            return new BaseResponse<>(postLoginRes);
        }
        catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
        
    }
    /*
     * 자동 로그인 API
     * [GET] /auth/jwt
     * @return BaseResponse<>(Integer)
     */
    @ResponseBody
    @GetMapping("/jwt")
    public BaseResponse<PostLoginRes> autoLogin(){

        try{
            if(jwtService.isExpiredJWT()){
                return new BaseResponse<>(EXPIRED_JWT);
            }
            int userIdxByJwt = jwtService.getUserIdx();
            String jwt = jwtService.getJwt();
            int userIdx = authProvider.getUserIdxByJWT(userIdxByJwt);
            PostLoginRes postLoginRes = authProvider.autoLogin(userIdx,jwt);
            return new BaseResponse<>(postLoginRes);
        }
        catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    /*
     * 로그아웃 API
     * [PATCH] /auth/logout
     */
    @ResponseBody
    @PatchMapping("/logout/{userIdx}")
    public BaseResponse<String> logout(@PathVariable("userIdx") int userIdx){

        try{
            int userIdxByJwt = jwtService.getUserIdx();
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(BaseResponseStatus.INVALID_JWT);
            }
            authService.logout(userIdx);
            return new BaseResponse<>("로그아웃 성공");
        }
        catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

}

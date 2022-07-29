package com.exerciseday.dev.src.user;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.exerciseday.dev.config.BaseException;
import com.exerciseday.dev.config.BaseResponse;

import com.exerciseday.dev.src.user.model.*;
import com.exerciseday.dev.utils.JwtService;


import static com.exerciseday.dev.config.BaseResponseStatus.*;
import static com.exerciseday.dev.utils.ValidationRegex.isRegexEmail;


@RestController
@RequestMapping("/users")
public class UserController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final UserProvider userProvider;
    @Autowired
    private final UserService userService;
    @Autowired
    private final JwtService jwtService;

    public UserController(UserProvider userProvider, UserService userService, JwtService jwtService){
        this.userProvider = userProvider;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    /**
     * 회원가입 API
     * [POST] /users
     * @return BaseResponse<PostUserRes>
     */
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostUserRes> createUser(@RequestBody PostUserReq postUserReq) {
        

        try{//형식 검증
            //이메일 입력 x
            if(postUserReq.getEmail() == null){
                return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
            }
            // 이메일 형식
            if(!isRegexEmail(postUserReq.getEmail())){
                return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
            }
            // 비밀번호 입력 x
            if(postUserReq.getPassword() == null){
                return new BaseResponse<>(POST_USERS_EMPTY_PASSWORD);
            }
            // 비밀번호 형식

            
            // 닉네임 입력 x
            if(postUserReq.getNickname() == null){
                return new BaseResponse<>(POST_USERS_EMPTY_NICKNAME);
            }

            // 닉네임 길이
            if(postUserReq.getNickname().length() > 10){
                return new BaseResponse<>(POST_USERS_INVALID_NICKNAME);
            }

            // 전화번호 입력 x
            if(postUserReq.getPhone() == null){
                return new BaseResponse<>(POST_USERS_EMPTY_PHONE);
            }
            // 전화번호 형식
            

            logger.info("[POST] /users 호출 성공 ############");
            PostUserRes postUserRes = userService.createUser(postUserReq);            
            return new BaseResponse<>(postUserRes);
        } catch(BaseException exception){

            return new BaseResponse<>((exception.getStatus()));
        }
    }

    

    /**
     * 회원 조회 API
     * [GET] /users
     * 이메일 검색 조회 API. 이메일을 입력 받아서 해당 이메일 주인 정보 반환
     * [GET] /users? Email=
     * @return BaseResponse<GetUserRes>
     */
    /*
    @ResponseBody
    @GetMapping("") // [GET] localhost:9000/users
    public BaseResponse<GetUserRes> getUserByEmail(@RequestParam(required = true) String Email){
        try
        {
                        
            if(Email == null)
            {
                return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
            }
            if(!isRegexEmail(Email)){
                return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
                
            }
            GetUserRes getUserRes = userProvider.getUserByEmail(Email);
            return new BaseResponse<>(getUserRes);
        }
        catch(BaseException exception)
        {
            return new BaseResponse<>((exception.getStatus()));

        }
    }
    */


    /**
     * userIdx 검색 조회 API. userIdx 입력 받아서 해당 유저 정보 반환.
     * [GET] /users/{userIdx}
     * @return BaseResponse<GetUserRes>
     */

     /*
    @ResponseBody
    @GetMapping("/{userIdx}") // [GET] /users/:userIdx
    public BaseResponse<GetUserRes> getUserByIdx(@PathVariable("userIdx") int userIdx){
        try{
            GetUserRes getUserRes = userProvider.getUserByIdx(userIdx);
            return new BaseResponse<>(getUserRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    */

}

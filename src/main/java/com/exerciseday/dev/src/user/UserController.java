package com.exerciseday.dev.src.user;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.exerciseday.dev.config.BaseException;
import com.exerciseday.dev.config.BaseResponse;
import com.exerciseday.dev.config.BaseResponseStatus;
import com.exerciseday.dev.src.user.model.*;
import com.exerciseday.dev.utils.JwtService;
import com.exerciseday.dev.utils.SNSService;

import software.amazon.awssdk.services.sns.model.SnsException;

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
    @Autowired
    private final SNSService snsService;


    public UserController(UserProvider userProvider, UserService userService, JwtService jwtService, SNSService snsService){
        this.userProvider = userProvider;
        this.userService = userService;
        this.jwtService = jwtService;
        this.snsService = snsService;
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

    /*
     * 이메일 중복 확인 API. 이메일을 입력 받아서 DB에 존재 여부 확인.
     * [GET] /users/check/email/{email}
     * @return BaseResponse<Boolean>
     *  중복O : true / 중복X : false
     */
    @ResponseBody
    @GetMapping("/check/email/{email}")
    public BaseResponse<Boolean> checkEmailExist(@PathVariable("email") String email){
        try
        {
                        
            if(email == null)
            {
                return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
            }
            /*
            if(!isRegexEmail(email)){
                return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
                
            }
            */

            boolean isDuplicated;            
            if(userProvider.checkEmail(email)==1){
                isDuplicated = true;
            } else{
                isDuplicated = false;
            }

            return new BaseResponse<>(isDuplicated);
        }
        catch(BaseException exception)
        {
            return new BaseResponse<>((exception.getStatus()));

        }
    }

    /*
     *  닉네임 중복 확인 API. 닉네임을 입력 받아서 DB에 닉네임 존재 여부 확인.
     *  [GET] users/check/nickname/{nickname}
     *  @return BaseResponse<Boolean>
     *  중복O : true / 중복X : false
     */
    @ResponseBody
    @GetMapping("/check/nickname/{nickname}")
    public BaseResponse<Boolean> checkNicknameExist(@PathVariable("nickname") String nickname){
        try
        {
                        
            if(nickname == null)
            {
                return new BaseResponse<>(EMPTY_NICKNAME);
            }
            /*
            if(!isRegexEmail(email)){
                return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
                
            }
            */

            boolean isDuplicated;            
            if(userProvider.checkNicknameExist(nickname)==1){
                isDuplicated = true;
            } else{
                isDuplicated = false;
            }

            return new BaseResponse<>(isDuplicated);
        }
        catch(BaseException exception)
        {
            return new BaseResponse<>((exception.getStatus()));

        }
    }

    @ResponseBody
    @PostMapping("/sms")
    public BaseResponse<String> certify(@RequestParam String phone){
        try{
            if(phone == null){
                return new BaseResponse<>(EMPTY_PHONE);
            }
            if(phone.length()!=11){
                return new BaseResponse<>(INVALID_PHONE);
            }
            String code = snsService.sendSMS(phone);
            return new BaseResponse<>(code);
        }
        catch(SnsException exception)
        {
            return new BaseResponse<>(FAILED_MESSAGE);
        }
    }

    /*
     *  아이디(이메일) 찾기 API
     *  [GET] /users/findId?phone=01012341234
     *  @return BaseResponse<String>
     */
    @ResponseBody
    @GetMapping("/findId")
    public BaseResponse<GetUserFindEmailRes> getUserFindEmail(@RequestParam(required = true) String phone){

        if(phone == null){  
            return new BaseResponse<>(EMPTY_PHONE);
        }
        if(phone.length() != 11){
            return new BaseResponse<>(BaseResponseStatus.INVALID_PHONE);
        }

        try
        {

            GetUserFindEmailRes getUserFindEmailRes = userProvider.getUserFindEmail(phone);

            return new BaseResponse<>(getUserFindEmailRes);

        }
        catch(BaseException exception)
        {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /*
     * 비밀번호 찾기 API
     * [POST] /users/find/?phone=
     */
    @ResponseBody
    @GetMapping("/findPwd")
    public BaseResponse<String> getUserFindPwd(@RequestParam(required = true) String email, String phone){

        if(phone == null){  
            return new BaseResponse<>(EMPTY_PHONE);
        }
        if(phone.length() != 11){
            return new BaseResponse<>(BaseResponseStatus.INVALID_PHONE);
        }

        try
        {

            GetUserRes getUserRes = userProvider.getUserByPhone(phone);
            return new BaseResponse<>(getUserRes.getEmail());

        }
        catch(BaseException exception)
        {
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

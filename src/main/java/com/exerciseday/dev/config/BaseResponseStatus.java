package com.exerciseday.dev.config;

import lombok.Getter;


//변수명(성공여부[true OR false], 에러코드[int] , 출력 메세지[""]);
@Getter
public enum BaseResponseStatus {

    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true,1000,"요청 성공했습니다."),

    /**
     * 2000 : Request 오류
     */
    // Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false,2003,"권한이 없는 유저의 접근입니다."),

    // users
    USERS_EMPTY_USER_ID(false, 2010, "유저 아이디 값을 확인해주세요."),


    /*
     * 회원가입
     */
    // [POST] /users
    POST_USERS_EMPTY_EMAIL(false, 2015, "이메일을 입력해주세요."),
    POST_USERS_INVALID_EMAIL(false, 2016, "이메일 형식을 확인해주세요."),
    
    POST_USERS_EMPTY_NICKNAME(false,2018,"닉네임을 입력해주세요."),
    POST_USERS_INVALID_NICKNAME(false,2019,"닉네임 형식을 확인해주세요."),
    
    POST_USERS_INVALID_PASSWORD(false, 2021, "비밀번호 형식을 확인해주세요."),
    POST_USERS_EMPTY_PASSWORD(false, 2022, "비밀번호를 입력해주세요."),
    POST_USERS_EMPTY_PHONE(false, 2023, "전화번호를 입력해주세요."),
    POST_USERS_INVALID_PHONE(false, 2024, "전화번호를 확인해주세요."),
    






    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),

    // [POST] /users
    DUPLICATED_EMAIL(false, 3013, "중복된 이메일입니다."),
    DUPLICATED_NICKNAME(false, 3014, "중복된 닉네임입니다."),
    DUPLICATED_PHONE(false, 3015, "중복된 전화번호입니다."),
    // [POST] /auth/login
    FAILED_TO_LOGIN(false,3016,"없는 아이디거나 비밀번호가 틀렸습니다."),
    FAILED_MESSAGE(false,3017,"메세지 전송에 실패했습니다."),

    EXIST_NO_NICKNAME(false, 3030, "존재하지 않는 닉네임입니다."),
    EXIST_NO_EMAIL(false, 3031, "존재하지 않는 이메일입니다."),
    EXIST_NO_PHONE(false, 3032, "존재하지 않는 전화번호입니다."),
    EXIST_NO_USER(false, 3033, "존재하지 않는 유저입니다."),
    EXIST_NO_TRAINER(false,3034,"존재하지 않는 트레이너입니다."),
    EXIST_NO_EXERCISE(false,3035,"존재하지 않는 운동입니다."),

    INVALID_EMAIL(false,3040,"바르지 않은 이메일입니다."),
    INVALID_NICKNAME(false, 3041, "바르지 않은 닉네임입니다."),
    INVALID_PHONE(false, 3042, "바르지 않은 전화번호입니다."),
    INVALID_NAME(false,3043,"바르지 않은 이름입니다."),

    EMPTY_EMAIL(false,3050,"이메일을 입력해주세요."),
    EMPTY_NICKNAME(false,3051,"닉네임을 입력해주세요."),
    EMPTY_PHONE(false,3052,"전화번호를 입력해주세요."),
    EMPTY_PASSWORD(false,3053,"비밀번호를 입력해주세요."),
    EMPTY_ROUTINE(false,3054,"운동 루틴을 추가해주세요."),

    DIFFERENT_USERS(false,3060,"서로 다른 유저 정보를 입력했습니다."),
    DIFFERENT_PASSWORD(false,3061,"서로 다른 비밀번호를 입력해주세요."),
    



    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),

   

    PASSWORD_ENCRYPTION_ERROR(false, 4011, "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, 4012, "비밀번호 복호화에 실패하였습니다."),
 //[PATCH] /users/{userIdx}
    MODIFY_FAIL_NICKNAME(false,4014,"유저 닉네임 수정 실패"),
    MODIFY_FAIL_PASSWORD(false,4015,"유저 비밀번호 수정 실패"),
    MODIFY_FAIL_IMG(false,4016,"사진 수정 실패"),
    MODIFY_FAIL_GOAL(false,4017,"목표 수정 실패"),

    DELETE_FAIL_USER(false,4020,"유저 삭제 실패"),
    DELETE_FAIL_EXERCISE(false,4021,"운동 삭제 실패");
    
    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }

}

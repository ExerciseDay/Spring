package com.exerciseday.dev.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostUserReq {
    private String email;
    private String password;
    private String nickname;
    private String phone;
    // 웹 nickName email pwd 가입경로 건의사항
    // 앱 email name pwd

}

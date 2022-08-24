package com.exerciseday.dev.src.auth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private int userIdx;
    private String email;
    private String password;
    private String nickname;
    private String phone;
    private String userGender;
    private String userImg;
    private String userGoal;
    private String userCreate;
}

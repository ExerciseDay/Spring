package com.exerciseday.dev.src.user.model;

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
    private String gender;
    private String img;
    private String goal;
    private String create;
}

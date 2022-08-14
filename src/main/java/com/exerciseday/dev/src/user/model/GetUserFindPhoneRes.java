package com.exerciseday.dev.src.user.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetUserFindPhoneRes {
    private int userIdx;
    private String email;
    private String phone;
    private Date userCreate;
    private String userImg;
}

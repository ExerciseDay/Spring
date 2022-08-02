package com.exerciseday.dev.src.user.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetUserFindEmailRes {
    private String email;
    private Date userCreate;
}

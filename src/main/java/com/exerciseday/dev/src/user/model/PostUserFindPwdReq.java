package com.exerciseday.dev.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostUserFindPwdReq {
    private String email;
    private String phone;
    
}

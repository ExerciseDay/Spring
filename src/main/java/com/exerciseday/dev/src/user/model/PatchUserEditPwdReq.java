package com.exerciseday.dev.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PatchUserEditPwdReq {
    private int userIdx;
    //private String encryptPassword;
    private String password;

}

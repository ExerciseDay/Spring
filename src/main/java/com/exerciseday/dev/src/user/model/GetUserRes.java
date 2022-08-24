package com.exerciseday.dev.src.user.model;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetUserRes {
    private int userIdx;
    private String email;    
    private String nickName;
    private String phone;
}

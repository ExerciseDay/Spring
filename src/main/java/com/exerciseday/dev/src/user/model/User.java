package com.exerciseday.dev.src.user.model;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class User {
    private int userIdx;
    private String email;
    private String password;
    private String nickname;
    private String phone;

}

package com.exerciseday.dev.src.user.kakao;

import lombok.Data;

@Data
public class GetKakaoProfile {

    private String phone_number;
    private String email;

    public String getPhone_number() {
        return phone_number;
    }

    public String getEmail() {
        return email;
    }
}
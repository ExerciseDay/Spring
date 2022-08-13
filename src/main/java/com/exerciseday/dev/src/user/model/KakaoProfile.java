package com.exerciseday.dev.src.user.model;

import lombok.Data;

@Data
public class KakaoProfile {
    public Integer id;
    public String connectedAt;
    public Properties properties;
    public KakaoAccount kakaoAccount;

    // public int getId() {
    // return this.id;
    // }

    // public int setId() {
    // return this.id = id;
    // }

    // public Properties getProperties() {
    // return this.properties;
    // }

    // public Properties setProperties() {
    // return this.properties = properties;
    // }

    // public KakaoAccount getKakaoAccount(){
    // return this.kakaoAccount;
    // }

    // public KakaoAccount setKakaoAccount(){
    // return this.kakaoAccount = kakaoAccount;
    // }

    @Data
    public class Properties {
        public String nickname;
    }

    @Data
    public class KakaoAccount {
        public Boolean profile_nickname_needs_agreement;
        public Profile profile;
        public Boolean has_email;
        public Boolean email_needs_agreement;
        public Boolean is_email_valid;
        public Boolean is_email_verified;
        public String email;

        @Data
        public class Profile {
            public String nickname;
        }

        public String getNickname;

    }
}

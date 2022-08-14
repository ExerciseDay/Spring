package com.exerciseday.dev.src.user.kakao;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
public class KakaoProfile {
    public Integer id;
    public String connected_at;
    public Properties properties;
    public KakaoAccount kakao_account;
    public String phone_number;

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

    }
}

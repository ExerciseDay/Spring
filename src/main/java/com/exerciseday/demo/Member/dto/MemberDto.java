package com.exerciseday.demo.Member.dto;

import com.exerciseday.demo.Member.domain.entity.MemberEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberDto {
    private String email;
    private String name;
    private String password;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public MemberEntity toEntity() {
        return MemberEntity.builder()
                .email(email)
                .name(name)
                .password(password)
                .build();
    }

    @Builder
    public MemberDto(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }
}

package com.exerciseday.dev.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetUserCourseRes {
    private int userIdx;
    private String userNickname;
    private String userImg;
    private String userGoal;
    private List<GetUserCustomRes> customList;
    private List<GetUserExpertRes> expertList;
}

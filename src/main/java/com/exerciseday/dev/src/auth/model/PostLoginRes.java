package com.exerciseday.dev.src.auth.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostLoginRes {
    private int userIdx;
    private String jwt;
    private String userNickname;
    private String userImg;
    private String userGoal;
    private List<GetTagRes> tags;
    
    /*
     * jwt, userIdx, userName, userImg, userGoal, tags[{tagIdx, tagName,{eCourseIdx,eCourseImg,eCourseName},,,} ,,,] 태그 4개 정도, 운동 추천 3개 정도 ?
     */
}
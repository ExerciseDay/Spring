package com.exerciseday.dev.src.expert.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetExpertRoutineInfoRes {
    //expertIdx 일치하는 루틴 정보 갖고 와서, 다시 운동 정보 찾기
    private int expertRoutineIdx;
    //private int exerciseIdx;
    private String exerciseName;
    private String exercisePart;
    private String exerciseDetailPart;
    private String exerciseInfo;
    private String exerciseImg;
    private int exerciseTime;
    private int exerciseCalory;
    private String exerciseIntroduce;
    private int expertIdx;
}

package com.exerciseday.dev.src.custom.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetCustomRoutineInfoRes {
    //customIdx 일치하는 루틴 정보 갖고 와서, 다시 운동 정보 찾기
    /*
    private int customRoutineIdx;
    //private int exerciseIdx;
    private String exerciseName;
    private String exercisePart;
    private String exerciseDetailPart;
    private String exerciseInfo;
    private String exerciseImg;
    private int exerciseTime;
    private int exerciseCalory;
    private String exerciseIntroduce;
    private int customIdx;
    */
    private int customRoutineIdx;    
    private String exerciseName;
    private String exercisePart;
    private String exerciseDetailPart;
    private String exerciseInfo;
    private String exerciseImg;   
    private String exerciseIntroduce;
    private int rep;
    private int weight;
    private int set;
    
}

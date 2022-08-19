package com.exerciseday.dev.src.custom.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetRoutineInfo {
    public GetRoutineInfo(GetRoutineInfo routineInfo) {
    }
    private int exerciseIdx;
    private String exerciseName;
    private String exercisePart;
    private String exerciseDetailPart;
    private String exerciseIntroduce;
    private int rep;
    private int weight;
    private int set;
}

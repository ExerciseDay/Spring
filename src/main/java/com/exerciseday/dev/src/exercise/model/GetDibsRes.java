package com.exerciseday.dev.src.exercise.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetDibsRes {
    // userNickname, dibsCount, [{exIdx, exName, exPart, exDetailPart, exIntroduce} ,,, ]
    private int userIdx;
    private String userNickname;
    private int numOfDibs;
    private List<ExerciseInfo> exercises; 
}


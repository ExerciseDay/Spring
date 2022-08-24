package com.exerciseday.dev.src.exercise.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostExerciseReq{
    private String exerciseName;
    private String exercisePart;
    private String exerciseDetailPart;
    private String exerciseInfo;
    private String exerciseImg;
    private int exerciseTime;
    private int exerciseCalory;
    private String exerciseIntroduce;
}
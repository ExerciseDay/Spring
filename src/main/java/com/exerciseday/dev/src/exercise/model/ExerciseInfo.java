package com.exerciseday.dev.src.exercise.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ExerciseInfo {
    private int exerciseIdx;
    private String exerciseName;
    private String exercisePart;
    private String exerciseDetailPart;
    private String exerciseIntroduce;
}

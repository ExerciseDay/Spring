package com.exerciseday.dev.src.exercise.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostExerciseRes{
    private int exerciseIdx;
    private String exerciseName;
}
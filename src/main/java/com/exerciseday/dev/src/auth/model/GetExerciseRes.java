package com.exerciseday.dev.src.auth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetExerciseRes {
    private int exerciseIdx;
    private String exerciseName;
}

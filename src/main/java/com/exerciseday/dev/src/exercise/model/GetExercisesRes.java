package com.exerciseday.dev.src.exercise.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetExercisesRes {
    private int count;
    private List<ExerciseInfo> exercises;
}

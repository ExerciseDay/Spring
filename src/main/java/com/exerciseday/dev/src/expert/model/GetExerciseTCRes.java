package com.exerciseday.dev.src.expert.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetExerciseTCRes {
    private int exIdx;
    private int exTime;
    private int exCalory;
}

package com.exerciseday.dev.src.custom.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CustomRoutine {
    private int customRoutineIdx;    
    private int rep;
    private int weight;
    private int set;
    private int rest;
    private int exerciseIdx;
}

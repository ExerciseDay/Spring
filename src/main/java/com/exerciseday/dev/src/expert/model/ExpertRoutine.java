package com.exerciseday.dev.src.expert.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ExpertRoutine {
    private int exerciseIdx;
    private int rep;
    private int weight;
    private int set;
    private int rest;
}

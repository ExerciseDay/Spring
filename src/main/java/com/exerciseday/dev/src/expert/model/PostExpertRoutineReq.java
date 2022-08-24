package com.exerciseday.dev.src.expert.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostExpertRoutineReq {
    private int rep;
    private int weight;
    private int set;
    private int rest;
    private int exerciseIdx;
}

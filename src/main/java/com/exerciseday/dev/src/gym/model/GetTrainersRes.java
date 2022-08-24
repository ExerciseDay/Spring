package com.exerciseday.dev.src.gym.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetTrainersRes {
    // private int gymIdx;
    private String trainerName;
    private String trainerCareer;
    private String trainerIntroduce;
    private String trainerImg;
}

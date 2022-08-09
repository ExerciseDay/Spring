package com.exerciseday.dev.src.gym.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetGymRes {

    private int gymIdx;
    private String gymName;
    private String gymIntroduce;
    private String gymImg;
    private int gymDistance;
    private int spoint;
    
}

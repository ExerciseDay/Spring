package com.exerciseday.dev.src.gym.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetGymInfoRes {
    
    private int gymIdx;
    private String gymName;
    private String gymAddress;
    private String gymIntroduce;
    private String gymImg;
    private String gymTime;
    private int gymParking;
    private int gymSauna;
    private int gymCloths;
    private int gymShower;
    private int gymDistance;

}

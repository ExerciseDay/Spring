package com.exerciseday.dev.src.gym.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetGymListRes {
    private List<GetGymRes> getGym;
}

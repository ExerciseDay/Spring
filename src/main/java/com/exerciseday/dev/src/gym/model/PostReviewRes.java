package com.exerciseday.dev.src.gym.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostReviewRes {
    private int userIdx;
    private int gymIdx;
    private int rvIdx;
}

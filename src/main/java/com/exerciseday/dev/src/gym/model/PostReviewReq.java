package com.exerciseday.dev.src.gym.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostReviewReq {
    private String rvContent;
    private int rvSP;
}

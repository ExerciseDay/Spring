package com.exerciseday.dev.src.auth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetTagExpertInfoRes {
    private int expertIdx;
    private String expertName;
    private String expertImg;    
}

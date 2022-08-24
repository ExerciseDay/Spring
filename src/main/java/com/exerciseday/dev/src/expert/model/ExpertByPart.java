package com.exerciseday.dev.src.expert.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ExpertByPart {
    private int expertIdx;
    private String expertName;
    private String expertIntroduce;
    private int expertTime;
    private int expertCalory;
    private String part;
    private String detailPart;
}


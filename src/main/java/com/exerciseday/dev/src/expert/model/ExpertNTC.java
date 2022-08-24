package com.exerciseday.dev.src.expert.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ExpertNTC {
    private int expertIdx;
    private String expertName;
    private int expertTime;
    private int expertCalory;
}

package com.exerciseday.dev.src.expert.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Expert {
    private int expertIdx;
    private String expertName;
    private String expertPart;
    private String expertDetailPart;
    private int expertTime;
    private int expertCalory;
    private int trainerIdx;
}

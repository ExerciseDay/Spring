package com.exerciseday.dev.src.custom.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CustomNTC {
    private int customIdx;
    private String customName;
    private int customTime;
    private int customCalory;
}

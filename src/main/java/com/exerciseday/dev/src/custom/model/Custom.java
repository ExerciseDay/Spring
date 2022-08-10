package com.exerciseday.dev.src.custom.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Custom {
    private int customIdx;
    private String customName;
    private int customTime;
    private int customCalory;
    private int userIdx;
}

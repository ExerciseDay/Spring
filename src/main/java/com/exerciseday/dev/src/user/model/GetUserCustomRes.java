package com.exerciseday.dev.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetUserCustomRes {
    private int customIdx;
    private String customName;
    private int customTime;
    private int customCalory;
}

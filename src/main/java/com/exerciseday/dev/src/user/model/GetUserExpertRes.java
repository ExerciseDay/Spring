package com.exerciseday.dev.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetUserExpertRes {
    private int expertIdx;
    private String expertName;
    private int expertTime;
    private int expertCalory;
}

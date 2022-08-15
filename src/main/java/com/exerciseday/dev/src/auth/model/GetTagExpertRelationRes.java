package com.exerciseday.dev.src.auth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetTagExpertRelationRes {
    private int tagIdx;
    private int expertIdx;
}

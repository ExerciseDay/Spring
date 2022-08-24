package com.exerciseday.dev.src.expert.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetExpertRes {
    
    private ExpertNTC expertNTC;
    private List<GetExpertRoutineInfoRes> expertRoutineInfos;
}

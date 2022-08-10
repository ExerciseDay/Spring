package com.exerciseday.dev.src.expert.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
public class PostExpertReq {
    private int trainerIdx;
    private String expertName;
    private String expertPart;
    private String expertDetailPart;
    private List<ExpertRoutine> expertRoutines;
}

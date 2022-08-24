package com.exerciseday.dev.src.custom.model;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostCustomReq {
    private String customName;
    //private String customPart;
    //private String customDetailPart;
    private String customIntroduce;
    private List<PostCustomRoutineReq> exercises;
}

package com.exerciseday.dev.src.expert.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
public class GetExpertByPartRes {
    private String part;
    private String detailPart;
    private List<ExpertByPart> expertList;

}

package com.exerciseday.dev.src.expert.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetExpertByPartReq {
    private String part;
    private String detailPart;
}
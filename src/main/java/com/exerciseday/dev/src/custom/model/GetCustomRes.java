package com.exerciseday.dev.src.custom.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetCustomRes {
    private CustomNTC customNTC;
    private List<GetCustomRoutineInfoRes> customRoutineInfos;
}

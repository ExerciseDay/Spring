package com.exerciseday.dev.src.custom.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DeleteCustomRemoveRoutineReq {
    private List<Integer> customRoutineIdxs;
}

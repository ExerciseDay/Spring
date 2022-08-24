package com.exerciseday.dev.src.course.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetCourseRes {
    private int userIdx;
    private int cCourseIdx;

    private int eCourseIdx;
}

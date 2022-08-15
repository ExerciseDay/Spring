package com.exerciseday.dev.src.auth.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetTagRes {
    //tags[{tagIdx, tagName,{eCourseIdx,eCourseImg,eCourseName},,,} ,,,] 
    private int tagIdx;
    private String tagName;
    //private List<GetTagExpertRelationRes> tagExpertRelations;
    private List<GetTagExpertInfoRes> expertInfos; 
}

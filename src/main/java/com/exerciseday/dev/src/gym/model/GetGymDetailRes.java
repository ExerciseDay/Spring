package com.exerciseday.dev.src.gym.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetGymDetailRes {
    private GetGymInfoRes getGymInfo;
    private List<GetTrainersRes> getTrainers;
    private List<GetReviewRes> getReviews;
    private List<GetImgRes> getImg;
}

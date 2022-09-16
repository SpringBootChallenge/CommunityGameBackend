package com.springchallenge.gamebackend.dto.input.review;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
public class UpdateReviewDto {
    @Min(0)
    @Max(10)
    private int score;
    @Size(max = 5000)
    private String text;
}

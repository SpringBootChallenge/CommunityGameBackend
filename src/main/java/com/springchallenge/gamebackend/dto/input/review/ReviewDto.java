package com.springchallenge.gamebackend.dto.input.review;


import lombok.Data;

import javax.validation.constraints.*;

@Data
public class ReviewDto {
    @Min(0)
    @Max(10)
    private int score;
    @Size(max = 5000)
    private String text;
    @NotNull
    private String gameId;
    @NotNull
    private String userId;
}

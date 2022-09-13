package com.springchallenge.gamebackend.dto;


import lombok.Data;

@Data
public class ReviewDto {
    private int score;
    private String text;
    private String gameId;
    private String userId;

    public ReviewDto(){};

}

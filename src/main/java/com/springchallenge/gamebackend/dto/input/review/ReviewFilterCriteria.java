package com.springchallenge.gamebackend.dto.input.review;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReviewFilterCriteria implements Serializable {
    private String gameId;
    private String userId;
    private Integer page = 1;
    private Integer limit = 10;
}

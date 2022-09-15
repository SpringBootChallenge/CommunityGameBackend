package com.springchallenge.gamebackend.service.review;

import com.springchallenge.gamebackend.dto.input.review.ReviewDto;
import com.springchallenge.gamebackend.dto.output.review.ReviewDtoOutput;

public interface ReviewService {

    ReviewDtoOutput createReview(ReviewDto reviewDto, String userIdHeader);
}

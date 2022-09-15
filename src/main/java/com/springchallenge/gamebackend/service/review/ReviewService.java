package com.springchallenge.gamebackend.service.review;

import com.springchallenge.gamebackend.dto.input.review.ReviewDto;
import com.springchallenge.gamebackend.dto.input.review.UpdateReviewDto;
import com.springchallenge.gamebackend.dto.output.review.ReviewDtoOutput;
import com.springchallenge.gamebackend.model.Review;

public interface ReviewService {

    Review findById(String id);

    ReviewDtoOutput createReview(ReviewDto reviewDto, String userIdHeader);

    ReviewDtoOutput updateReview(UpdateReviewDto updateReviewDto, String userId, String reviewId);
}

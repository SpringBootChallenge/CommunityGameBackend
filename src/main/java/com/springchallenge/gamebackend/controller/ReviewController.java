package com.springchallenge.gamebackend.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import com.springchallenge.gamebackend.exception.ExceptionType;
import com.springchallenge.gamebackend.service.user.UserService;
import com.springchallenge.gamebackend.dto.input.review.ReviewDto;
import com.springchallenge.gamebackend.service.review.ReviewService;
import com.springchallenge.gamebackend.exception.ExceptionsGenerator;
import com.springchallenge.gamebackend.dto.input.review.UpdateReviewDto;
import com.springchallenge.gamebackend.dto.output.review.ReviewDtoOutput;
import com.springchallenge.gamebackend.dto.input.review.ReviewFilterCriteria;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    private final UserService userService;

    private static final String ERROR_MESSAGE = "You must be logged in to the server";

    public ReviewController(@Autowired ReviewService reviewService, @Autowired UserService userService) {
        this.reviewService = reviewService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ReviewDtoOutput> createReview(@RequestHeader(value = "User-id", required = true) String userIdHeader,
                                                        @RequestBody @Valid ReviewDto reviewDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()) throw ExceptionsGenerator.getException(ExceptionType.INVALID_OBJECT, "Incorrectly formed request");
        if(userService.isLogged(userIdHeader)){
            ReviewDtoOutput reviewDtoOutput = reviewService.createReview(reviewDto, userIdHeader);
            return new ResponseEntity<>(reviewDtoOutput, HttpStatus.CREATED);
        }
        throw ExceptionsGenerator.getException(ExceptionType.UNAUTHORIZED, ERROR_MESSAGE);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewDtoOutput> updateReview(@RequestHeader(value = "User-id", required = true) String userIdHeader,
                                                        @RequestBody @Valid UpdateReviewDto updateReviewDto, BindingResult bindingResult,
                                                        @PathVariable String reviewId){
        if(bindingResult.hasErrors()) throw ExceptionsGenerator.getException(ExceptionType.INVALID_OBJECT, "Incorrectly formed request");
        if(userService.isLogged(userIdHeader)){
            ReviewDtoOutput reviewDtoOutput = reviewService.updateReview(updateReviewDto, userIdHeader, reviewId);
            return new ResponseEntity<>(reviewDtoOutput, HttpStatus.OK);
        }
        throw ExceptionsGenerator.getException(ExceptionType.UNAUTHORIZED, ERROR_MESSAGE);
    }

    @DeleteMapping("/{reviewId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReview(@RequestHeader(value = "User-id", required = true) String userIdHeader, @PathVariable String reviewId){
        if(userService.isLogged(userIdHeader)){
            reviewService.deleteReview(userIdHeader, reviewId);
        }
        throw ExceptionsGenerator.getException(ExceptionType.UNAUTHORIZED, ERROR_MESSAGE);
    }

    @GetMapping
    public ResponseEntity<List<ReviewDtoOutput>> getGames(@ParameterObject ReviewFilterCriteria filter) {
        return new ResponseEntity<>(
                reviewService.getFilteredReviews(filter),
                HttpStatus.OK);
    }
}

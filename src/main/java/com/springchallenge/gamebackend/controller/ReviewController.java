package com.springchallenge.gamebackend.controller;


import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.beans.factory.annotation.Autowired;

import com.springchallenge.gamebackend.exception.ExceptionType;
import com.springchallenge.gamebackend.dto.input.review.ReviewDto;
import com.springchallenge.gamebackend.service.review.ReviewService;
import com.springchallenge.gamebackend.exception.ExceptionsGenerator;
import com.springchallenge.gamebackend.dto.output.review.ReviewDtoOutput;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @GetMapping("/health-check")
    public ResponseEntity<String> healthCheck(@RequestHeader(value = "user-id", required = false) String optionalHeader){
        return ResponseEntity.ok("OK");
    }

    @PostMapping
    public ResponseEntity<ReviewDtoOutput> createReview(@RequestBody @Valid ReviewDto reviewDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()) throw ExceptionsGenerator.getException(ExceptionType.INVALID_OBJECT, "Incorrectly formed request");
        ReviewDtoOutput reviewDtoOutput = reviewService.createReview(reviewDto);
        return new ResponseEntity<>(reviewDtoOutput, HttpStatus.CREATED);
    }
}

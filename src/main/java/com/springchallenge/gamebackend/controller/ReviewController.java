package com.springchallenge.gamebackend.controller;

import com.springchallenge.gamebackend.dto.ReviewDto;
import com.springchallenge.gamebackend.model.Review;
import com.springchallenge.gamebackend.services.ReviewService.ReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;


    @GetMapping("/health-check")
    public ResponseEntity<String> healthCheck(@RequestHeader(value = "user-id", required = false) String optionalHeader){
        System.out.println("Service");
        System.out.println(optionalHeader);
        return ResponseEntity.ok("OK");
    }

    @PostMapping
    public ResponseEntity<ReviewDto> createReview(@RequestHeader(value = "user-id", required = false) String optionalHeader,
                                                    @RequestBody ReviewDto reviewDto){
        System.out.println("Service");
        ModelMapper modelMapper = new ModelMapper();
        System.out.println(optionalHeader);

        return new ResponseEntity<>(reviewDto, HttpStatus.CREATED);
    }
}

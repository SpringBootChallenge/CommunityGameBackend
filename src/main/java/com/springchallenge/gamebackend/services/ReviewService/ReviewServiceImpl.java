package com.springchallenge.gamebackend.services.ReviewService;

import com.springchallenge.gamebackend.model.Review;
import com.springchallenge.gamebackend.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Laura Garcia
 */
@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public Review createReview(Review review) {
        reviewRepository.save(review);
        return review;
    }
}

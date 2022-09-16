package com.springchallenge.gamebackend.service.review;

import com.springchallenge.gamebackend.dto.input.review.ReviewFilterCriteria;
import com.springchallenge.gamebackend.dto.input.review.UpdateReviewDto;
import org.modelmapper.ModelMapper;

import com.springchallenge.gamebackend.model.User;
import com.springchallenge.gamebackend.model.Game;
import com.springchallenge.gamebackend.model.Review;
import com.springchallenge.gamebackend.exception.ExceptionType;
import com.springchallenge.gamebackend.service.game.GameService;
import com.springchallenge.gamebackend.service.user.UserService;
import com.springchallenge.gamebackend.dto.input.review.ReviewDto;
import com.springchallenge.gamebackend.repository.ReviewRepository;
import com.springchallenge.gamebackend.exception.ExceptionsGenerator;
import com.springchallenge.gamebackend.dto.output.review.ReviewDtoOutput;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private UserService userService;

    @Autowired
    private GameService gameService;

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public Review findById(String id) {
        Optional<Review> review = reviewRepository.findById(id);
        if (review.isPresent()) {
            return reviewRepository.findById(id).get();
        }
        throw ExceptionsGenerator.getException(ExceptionType.NOT_FOUND,
                "There is no review with the supplied id.");
    }

    @Override
    public ReviewDtoOutput createReview(ReviewDto reviewDto, String userIdHeader) {
        String userId = reviewDto.getUserId();
        if(!userId.equals(userIdHeader)) throw ExceptionsGenerator.getException(ExceptionType.UNAUTHORIZED, "You can only create reviews for yourself");
        String gameId = reviewDto.getGameId();
        Boolean reviewCreated = reviewRepository.findByGameIdAndUserId(gameId, userId) != null;
        if(!reviewCreated){
            User user = userService.findById(userId);
            Game game = gameService.findById(gameId);
            Review review = new Review(reviewDto, user, game);
            reviewRepository.save(review);
            ModelMapper mapper = new ModelMapper();
            ReviewDtoOutput reviewDtoOutput = mapper.map(review, ReviewDtoOutput.class);
            return reviewDtoOutput;
        }
        throw ExceptionsGenerator.getException(ExceptionType.DUPLICATE_ENTITY, "Review already created");
    }

    @Override
    public ReviewDtoOutput updateReview(UpdateReviewDto updateReviewDto, String userId, String reviewId) {
        if(reviewRepository.findByUserIdAndId(userId, reviewId) == null) throw ExceptionsGenerator.getException(ExceptionType.UNAUTHORIZED, "You can only update your own reviews");
        Review review = findById(reviewId);
        review.update(updateReviewDto);
        reviewRepository.save(review);
        ModelMapper mapper = new ModelMapper();
        ReviewDtoOutput reviewDtoOutput = mapper.map(review, ReviewDtoOutput.class);
        return reviewDtoOutput;
    }

    @Override
    public void deleteReview(String userId, String reviewId) {
        if(reviewRepository.findByUserIdAndId(userId, reviewId) == null) throw ExceptionsGenerator.getException(ExceptionType.UNAUTHORIZED, "You can only delete your own reviews");
        reviewRepository.deleteById(reviewId);
    }

//    @Override
//    public List<ReviewDtoOutput> getFilteredGames(ReviewFilterCriteria filter) {
//        List<Review> reviews= new ArrayList<>();
//        Pageable pagination = PageRequest.of(filter.getPage() - 1, filter.getLimit());
//        reviewRepository.findByFilter(filter.getGameId(), filter.getUserId(), pagination);
//        return null;
//    }


}

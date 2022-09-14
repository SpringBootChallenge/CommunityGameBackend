package com.springchallenge.gamebackend.service.review;

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

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private UserService userService;

    @Autowired
    private GameService gameService;

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public ReviewDtoOutput createReview(ReviewDto reviewDto) {
        String userId = reviewDto.getUserId();
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
        }else{
            throw ExceptionsGenerator.getException(ExceptionType.DUPLICATE_ENTITY, "Review already created");
        }
    }
}

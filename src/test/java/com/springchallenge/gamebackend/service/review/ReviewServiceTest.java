package com.springchallenge.gamebackend.service.review;

import com.springchallenge.gamebackend.dto.input.review.ReviewDto;
import com.springchallenge.gamebackend.exception.customexceptions.ObjectNotFoundException;
import com.springchallenge.gamebackend.exception.customexceptions.UnauthorizedException;
import com.springchallenge.gamebackend.model.Game;
import com.springchallenge.gamebackend.model.GameState;
import com.springchallenge.gamebackend.model.GameStateKey;
import com.springchallenge.gamebackend.repository.GameStateRepository;
import com.springchallenge.gamebackend.repository.ReviewRepository;
import com.springchallenge.gamebackend.service.game.GameService;
import com.springchallenge.gamebackend.service.gamestate.GameStateServiceImpl;
import com.springchallenge.gamebackend.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class ReviewServiceTest {
    @InjectMocks
    private ReviewServiceImpl reviewService;
    @Mock
    private GameService gameService;
    @Mock
    private UserService userService;
    @Mock
    private ReviewRepository reviewRepo;

    @Test
    void createReview_reviewAnotherUser_throwUnauthorizedException() {
        // Arrange
        String userId = "00018a93-51a7-4928-b869-77a2a75695fb";
        ReviewDto invalidDto= new ReviewDto();
        invalidDto.setUserId("DIFFERENT-ID");
        // act
        Exception exception = assertThrows(UnauthorizedException.class, () -> {
            reviewService.createReview(invalidDto, userId);
        });
        // assert
        assertEquals("You can only create reviews for yourself", exception.getMessage());
    }

}

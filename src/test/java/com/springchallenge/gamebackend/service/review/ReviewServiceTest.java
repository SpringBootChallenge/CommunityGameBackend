package com.springchallenge.gamebackend.service.review;

import com.springchallenge.gamebackend.dto.input.review.ReviewDto;
import com.springchallenge.gamebackend.dto.input.review.UpdateReviewDto;
import com.springchallenge.gamebackend.dto.output.review.ReviewDtoOutput;
import com.springchallenge.gamebackend.dto.output.user.UserDto;
import com.springchallenge.gamebackend.exception.customexceptions.DuplicateEntityException;
import com.springchallenge.gamebackend.exception.customexceptions.ObjectNotFoundException;
import com.springchallenge.gamebackend.exception.customexceptions.UnauthorizedException;
import com.springchallenge.gamebackend.model.*;
import com.springchallenge.gamebackend.repository.GameStateRepository;
import com.springchallenge.gamebackend.repository.ReviewRepository;
import com.springchallenge.gamebackend.service.game.GameService;
import com.springchallenge.gamebackend.service.gamestate.GameStateServiceImpl;
import com.springchallenge.gamebackend.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

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

    @Test
    void createReview_reviewAlreadyCreated_throwDuplicateEntityException() {
        // Arrange
        String userId = "00018a93-51a7-4928-b869-77a2a75695fb";
        ReviewDto validDto= new ReviewDto();
        validDto.setUserId(userId);
        Review createdReview= Mockito.mock(Review.class);
        when(reviewRepo.findByGameIdAndUserId(validDto.getGameId(),userId)).thenReturn(createdReview);
        // act
        Exception exception = assertThrows(DuplicateEntityException.class, () -> {
            reviewService.createReview(validDto, userId);
        });
        // assert
        assertEquals("Review already created", exception.getMessage());
    }


    @Test
    void createReview_validNewReview_saveAndReturn() {
        // Arrange
        String userId = "00018a93-51a7-4928-b869-77a2a75695fb";
        ReviewDto validDto= Mockito.mock(ReviewDto.class);
        when(validDto.getUserId()).thenReturn(userId);
        when(reviewRepo.findByGameIdAndUserId(validDto.getGameId(),userId)).thenReturn(null);
        User existingUser= Mockito.mock(User.class);
        Game existingGame= Mockito.mock(Game.class);
        when(userService.findById(userId)).thenReturn(existingUser);
        when(gameService.findById(validDto.getGameId())).thenReturn(existingGame);
        Review expectedReview= new Review();
        expectedReview.setGame(existingGame);
        expectedReview.setUser(existingUser);
        LocalDateTime nowDate = LocalDateTime.now();
        UUID testUUID= java.util.UUID.randomUUID();
        System.out.println(nowDate.toString()+testUUID.toString());
        expectedReview.setId(testUUID.toString());
        expectedReview.setTimeStamp(nowDate);
        ReviewDtoOutput expectedDto= new ModelMapper().map(expectedReview, ReviewDtoOutput.class);
        // act
        ReviewDtoOutput createdReviewDto=null;
        try (MockedStatic<LocalDateTime> mockedLocalDateTime = Mockito.mockStatic(LocalDateTime.class)) {
            mockedLocalDateTime.when(LocalDateTime::now).thenReturn(nowDate);
            try (MockedStatic<UUID> mockedUUID = Mockito.mockStatic(UUID.class)) {
                mockedUUID.when(UUID::randomUUID).thenReturn(testUUID);
                createdReviewDto= reviewService.createReview(validDto, userId);
            }
        }

        // assert
        verify(reviewRepo).save(expectedReview);
        assertEquals(expectedDto, createdReviewDto);
    }

    @Test
    void updateReview_nonExistentReview_throwUnauthorizedException() {
        // Arrange
        String userId = "00018a93-51a7-4928-b869-77a2a75695fb";
        String reviewId = "00018ds-f4vdd4-b869-77a2a75695fb";
        UpdateReviewDto inputReviewDto= Mockito.mock(UpdateReviewDto.class);
        when(reviewRepo.findByUserIdAndId(userId,reviewId)).thenReturn(null);
        // act
        Exception exception = assertThrows(UnauthorizedException.class, () -> {
            reviewService.updateReview(inputReviewDto, userId,reviewId);
        });
        // assert
        assertEquals("You can only update your own reviews", exception.getMessage());
    }

    @Test
    void updateReview_validReview_callRepoToSave() {
        // Arrange
        String userId = "00018a93-51a7-4928-b869-77a2a75695fb";
        String reviewId = "00018ds-f4vdd4-b869-77a2a75695fb";
        UpdateReviewDto inputReviewDto= Mockito.mock(UpdateReviewDto.class);
        Review expectedReview= new Review();
        when(reviewRepo.findByUserIdAndId(userId,reviewId)).thenReturn(expectedReview);
        when(reviewRepo.findById(reviewId)).thenReturn(Optional.of(expectedReview));
        expectedReview.update(inputReviewDto);
        ReviewDtoOutput expectedDto=new ModelMapper().map(expectedReview, ReviewDtoOutput.class);
        // act
        ReviewDtoOutput updatedReview= reviewService.updateReview(inputReviewDto, userId,reviewId);
        // assert
        verify(reviewRepo).save(expectedReview);
        assertEquals(expectedDto, updatedReview);
    }

    @Test
    void findById_nonExistentReview_throwObjectNotFoundException() {
        // Arrange
        String nonExistentReview = "NON-EXISTENT";
       when(reviewRepo.findById(nonExistentReview)).thenReturn(Optional.empty());
        // act
        Exception exception = assertThrows(ObjectNotFoundException.class, () -> {
            reviewService.findById(nonExistentReview);
        });
        // assert
        assertEquals( "There is no review with the supplied id.", exception.getMessage());
    }

    @Test
    void findById_existentReview_returnReview() {
        // Arrange
        String reviewId = "00018a93-51a7-4928-b869-77a2a75695fb";
        Review expectedReview= Mockito.mock(Review.class);
        when(reviewRepo.findById(reviewId)).thenReturn(Optional.of(expectedReview));
        // act
        Review actualReview=reviewService.findById(reviewId);
        // assert
        assertEquals( expectedReview,actualReview);
    }

}

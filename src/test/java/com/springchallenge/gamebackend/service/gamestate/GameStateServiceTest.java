package com.springchallenge.gamebackend.service.gamestate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.springchallenge.gamebackend.exception.customexceptions.ObjectNotFoundException;
import com.springchallenge.gamebackend.model.Game;
import com.springchallenge.gamebackend.model.GameState;
import com.springchallenge.gamebackend.model.GameStateKey;
import com.springchallenge.gamebackend.repository.GameStateRepository;
import com.springchallenge.gamebackend.service.game.GameService;

@ExtendWith(SpringExtension.class)
class GameStateServiceTest {

    @InjectMocks
    private GameStateServiceImpl gameStateService;
    @Mock
    private GameService gameService;
    @Mock
    private GameStateRepository gameStateRepo;

    @Test
    void removeGameState_existentGameState_callRepoToDelete() {
        // Arrange
        String gameId = "de6fb166-5170-4fae-abda-603110da71c3";
        String userId = "00018a93-51a7-4928-b869-77a2a75695fb";
        Game foundGame = Mockito.mock(Game.class);
        GameState foundState = Mockito.mock(GameState.class);
        GameStateKey gameStateKey = new GameStateKey(gameId, userId);
        when(gameStateRepo.findById(gameStateKey)).thenReturn(Optional.of(foundState));
        when(gameService.findById(gameId)).thenReturn(foundGame);
        // act
        gameStateService.removeGameState(gameId, userId);
        // assert
        verify(gameStateRepo).delete(foundState);
    }

    @Test
    void removeGameState_inexistentGameState_throwsObjectNotFoundException() {
        // arrange
        String gameId = "de6fb166-5170-4fae-abda-603110da71c3";
        String userId = "00018a93-51a7-4928-b869-77a2a75695fb";
        GameStateKey gameStateKey = new GameStateKey(gameId, userId);
        when(gameStateRepo.findById(gameStateKey)).thenReturn(Optional.empty());
        Game foundGame = Mockito.mock(Game.class);
        when(gameService.findById(gameId)).thenReturn(foundGame);
        // act
        Exception exception = assertThrows(ObjectNotFoundException.class, () -> {
            gameStateService.removeGameState(gameId, userId);
        });
        // assert
        assertEquals("The current user is not related to the supplied game.", exception.getMessage());
    }

    @Test
    void removeGameState_inexistentGame_throwsObjectNotFoundException() {
        // arrange
        String gameId = "de6fb166-5170-4fae-abda-603110da71c3";
        String userId = "00018a93-51a7-4928-b869-77a2a75695fb";
        String expectedMessage = "There is no game with the supplied id.";
        when(gameService.findById(gameId)).thenThrow(new ObjectNotFoundException(expectedMessage));
        // act
        Exception exception = assertThrows(ObjectNotFoundException.class, () -> {
            gameStateService.removeGameState(gameId, userId);
        });
        // assert
        assertEquals(expectedMessage, exception.getMessage());
    }

}

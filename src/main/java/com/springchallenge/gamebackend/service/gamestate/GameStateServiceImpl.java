package com.springchallenge.gamebackend.service.gamestate;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springchallenge.gamebackend.dto.input.gamestate.GameStateDto;
import com.springchallenge.gamebackend.exception.ExceptionType;
import com.springchallenge.gamebackend.exception.ExceptionsGenerator;
import com.springchallenge.gamebackend.model.Game;
import com.springchallenge.gamebackend.model.GameState;
import com.springchallenge.gamebackend.model.GameStateKey;
import com.springchallenge.gamebackend.model.State;
import com.springchallenge.gamebackend.model.User;
import com.springchallenge.gamebackend.repository.GameStateRepository;
import com.springchallenge.gamebackend.repository.StateRepository;
import com.springchallenge.gamebackend.service.game.GameService;
import com.springchallenge.gamebackend.service.user.UserService;

@Service
public class GameStateServiceImpl implements GameStateService {

    @Autowired
    private GameStateRepository gameStateRepo;
    @Autowired
    private StateRepository stateRepo;
    @Autowired
    private UserService userService;
    @Autowired
    private GameService gameService;

    @Override
    public void setGameState(String userId, String gameId, GameStateDto stateData) {
        User user = userService.findById(userId);
        Game game = gameService.findById(gameId);
        Optional<GameState> possibleGameState = findPossibleGameState(gameId, userId);
        GameState newState = new GameState();
        if (possibleGameState.isPresent()) {
            newState = possibleGameState.get();
        }
        Optional<State> state = stateRepo.findByDescription(stateData.getState());
        if (state.isPresent()) {
            newState.setUser(user);
            newState.setGame(game);
            newState.setState(state.get());
            newState.setId(new GameStateKey(gameId, userId));
            saveGameState(newState);
        } else {
            throw ExceptionsGenerator.getException(ExceptionType.INVALID_OBJECT, "The given state does not exist.");
        }

    }

    public GameState saveGameState(GameState state) {
        return gameStateRepo.save(state);
    }

    @Override
    public Optional<GameState> findPossibleGameState(String gameId, String userId) {
        return gameStateRepo.findById(new GameStateKey(gameId, userId));
    }

}

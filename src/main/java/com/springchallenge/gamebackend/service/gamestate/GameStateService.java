package com.springchallenge.gamebackend.service.gamestate;

import java.util.Optional;

import com.springchallenge.gamebackend.dto.input.gamestate.GameStateDto;
import com.springchallenge.gamebackend.model.GameState;

public interface GameStateService {

    public void setGameState(String userId, String gameId, GameStateDto state);

    public Optional<GameState> findPossibleGameState(String gameId, String game);
}

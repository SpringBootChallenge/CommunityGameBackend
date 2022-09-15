package com.springchallenge.gamebackend.repository;

import org.springframework.data.repository.CrudRepository;

import com.springchallenge.gamebackend.model.GameState;
import com.springchallenge.gamebackend.model.GameStateKey;

public interface GameStateRepository extends CrudRepository<GameState, GameStateKey> {
}

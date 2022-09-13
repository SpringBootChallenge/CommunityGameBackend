package com.springchallenge.gamebackend.service.game;

import java.util.List;

import com.springchallenge.gamebackend.model.Game;

public interface GameService {

    public Boolean loadGamesFromCSV();

    public Boolean saveGames(List<Game> games);
}

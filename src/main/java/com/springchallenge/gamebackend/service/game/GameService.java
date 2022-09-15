package com.springchallenge.gamebackend.service.game;

import java.util.List;

import com.springchallenge.gamebackend.dto.input.game.GameFilterCriteria;
import com.springchallenge.gamebackend.dto.output.game.GameDto;
import com.springchallenge.gamebackend.model.Game;

public interface GameService {

    public void loadGamesFromCSV();

    public void saveGames(List<Game> games);

    public GameDto getGameById(String id);

    public List<GameDto> getFilteredGames(GameFilterCriteria filter);
}

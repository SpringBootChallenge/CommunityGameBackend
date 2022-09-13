package com.springchallenge.gamebackend.service.game;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springchallenge.gamebackend.model.Game;
import com.springchallenge.gamebackend.repository.GameRepository;
import com.springchallenge.gamebackend.util.CSVReader;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private CSVReader csvReader;
    @Autowired
    private GameRepository gameRepo;

    @Override
    @Transactional
    public Boolean loadGamesFromCSV() {
        List<Game> newGames = csvReader.loadGamesFromCsv();
        return saveGames(newGames);
    }

    @Override
    public Boolean saveGames(List<Game> games) {
        gameRepo.saveAll(games);
        return true;
    }

}
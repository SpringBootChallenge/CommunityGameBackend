package com.springchallenge.gamebackend.service.game;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.springchallenge.gamebackend.model.Game;
import com.springchallenge.gamebackend.util.CSVReader;
import com.springchallenge.gamebackend.dto.output.game.GameDto;
import com.springchallenge.gamebackend.exception.ExceptionType;
import com.springchallenge.gamebackend.repository.GameRepository;
import com.springchallenge.gamebackend.exception.ExceptionsGenerator;
import com.springchallenge.gamebackend.dto.input.game.GameFilterCriteria;

@Service
public class GameServiceImpl implements GameService {

    private final CSVReader csvReader;

    private final ModelMapper modelMapper;

    private final GameRepository gameRepository;

    public GameServiceImpl(@Autowired CSVReader csvReader, @Autowired GameRepository gameRepository, @Autowired ModelMapper modelMapper){
        this.csvReader = csvReader;
        this.modelMapper = modelMapper;
        this.gameRepository = gameRepository;
    }

    @Override
    @Transactional
    public void loadGamesFromCSV() {
        List<Game> newGames = csvReader.loadGamesFromCsv();
        saveGames(newGames);
    }

    @Override
    public void saveGames(List<Game> games) {
        try {
            gameRepository.saveAll(games);
        } catch (DataAccessException e) {
            throw ExceptionsGenerator.getException(ExceptionType.INVALID_OBJECT,
                    "The games could not be stored in the database.");
        }
    }

    @Override
    public Game findById(String id) {
        Optional<Game> possibleGame = findPossibleGameById(id);
        if (possibleGame.isPresent()) {
            return possibleGame.get();
        }
        throw ExceptionsGenerator.getException(ExceptionType.NOT_FOUND,
                "There is no game with the supplied id.");
    }

    private void assignGameStatistics(GameDto game) {
        game.setBacklogCount(gameRepository.countByState(game.getId(), "BACKLOG"));
        game.setBeatCount(gameRepository.countByState(game.getId(), "BEAT"));
        game.setRetiredCount(gameRepository.countByState(game.getId(), "RETIRED"));
        game.setPlayingCount(gameRepository.countByState(game.getId(), "PLAYING"));
    }

    public List<GameDto> getFilteredGames(GameFilterCriteria filter) {
        List<Game> games = new ArrayList<>();
        Pageable pagination = PageRequest.of(filter.getPage() - 1, filter.getLimit());
        switch (filter.getSort()){
            case newest:
                games = gameRepository.findByFiltersOrderByReleaseDate(filter.getPlatform(), filter.getGenre(),
                        filter.getTitle(), pagination);
                break;
            case score:
                games = gameRepository.findByFiltersOrderByScore(filter.getPlatform(), filter.getGenre(),
                        filter.getTitle(), pagination);
                break;
            case players:
                games = gameRepository.findByFiltersOrderByPlayers(filter.getPlatform(), filter.getGenre(),
                        filter.getTitle(), "PLAYING", pagination);
                break;
        }
        return games
                .stream()
                .map(game -> {
                    GameDto gameDto = modelMapper.map(game, GameDto.class);
                    assignGameStatistics(gameDto);
                    return gameDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public GameDto findGameDtoById(String id) {
        Game game = findById(id);
        GameDto foundGame = modelMapper.map(game, GameDto.class);
        assignGameStatistics(foundGame);
        return foundGame;
    }

    @Override
    public Optional<Game> findPossibleGameById(String id) {
        return gameRepository.findById(id);
    }

}
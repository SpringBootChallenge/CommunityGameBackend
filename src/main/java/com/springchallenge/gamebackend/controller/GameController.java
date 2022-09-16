package com.springchallenge.gamebackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.springchallenge.gamebackend.dto.input.game.GameFilterCriteria;
import com.springchallenge.gamebackend.dto.input.gamestate.GameStateDto;
import com.springchallenge.gamebackend.dto.output.game.GameDto;
import com.springchallenge.gamebackend.exception.ExceptionType;
import com.springchallenge.gamebackend.exception.ExceptionsGenerator;
import com.springchallenge.gamebackend.service.game.GameService;
import com.springchallenge.gamebackend.service.gamestate.GameStateService;
import com.springchallenge.gamebackend.service.user.UserService;

@RestController
@RequestMapping("/games")
public class GameController {

    @Autowired
    private GameService gameService;
    @Autowired
    private GameStateService gameStateService;
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<GameDto> getGame(@PathVariable String id) {
        GameDto game = gameService.findGameDtoById(id);
        return new ResponseEntity<>(game, HttpStatus.OK);
    }

    @GetMapping("/load")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void loadGamesFromCSV() {
        gameService.loadGamesFromCSV();
    }

    @GetMapping
    public ResponseEntity<List<GameDto>> getGames(GameFilterCriteria filter) {
        return new ResponseEntity<>(
                gameService.getFilteredGames(filter),
                HttpStatus.OK);
    }

    @PostMapping("/{gameId}/state")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setGameState(@RequestHeader(value = "user-id", required = true) String userIdHeader,
            @PathVariable String gameId, @RequestBody GameStateDto state) {
        if (userService.isLogged(userIdHeader)) {
            gameStateService.setGameState(userIdHeader, gameId, state);
        } else {
            throw ExceptionsGenerator.getException(ExceptionType.UNAUTHORIZED, "You must be logged in to the server");
        }
    }

    @DeleteMapping("/{gameId}/state")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setGameState(@RequestHeader(value = "user-id", required = true) String userIdHeader,
            @PathVariable String gameId) {
        if (userService.isLogged(userIdHeader)) {
            gameStateService.removeGameState(gameId, userIdHeader);
        } else {
            throw ExceptionsGenerator.getException(ExceptionType.UNAUTHORIZED, "You must be logged in to the server");
        }
    }
}

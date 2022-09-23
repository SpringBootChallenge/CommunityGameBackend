package com.springchallenge.gamebackend.controller;

import java.util.List;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.springchallenge.gamebackend.service.user.UserService;
import com.springchallenge.gamebackend.dto.output.game.GameDto;
import com.springchallenge.gamebackend.exception.ExceptionType;
import com.springchallenge.gamebackend.service.game.GameService;
import com.springchallenge.gamebackend.exception.ExceptionsGenerator;
import com.springchallenge.gamebackend.dto.input.gamestate.GameStateDto;
import com.springchallenge.gamebackend.dto.input.game.GameFilterCriteria;
import com.springchallenge.gamebackend.service.gamestate.GameStateService;

@RestController
@RequestMapping("/games")
public class GameController {

    private final GameService gameService;

    private final UserService userService;

    private final GameStateService gameStateService;

    public GameController(@Autowired GameService gameService, @Autowired GameStateService gameStateService, @Autowired UserService userService) {
        this.gameService = gameService;
        this.userService = userService;
        this.gameStateService = gameStateService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameDto> getGame(@PathVariable String id) {
        GameDto game = gameService.findGameDtoById(id);
        return new ResponseEntity<>(game, HttpStatus.OK);
    }

    @PostMapping("/load")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void loadGamesFromCSV() {
        gameService.loadGamesFromCSV();
    }

    @GetMapping
    public ResponseEntity<List<GameDto>> getGames(@ParameterObject @Valid GameFilterCriteria filter, BindingResult validationResults) {
        if (validationResults.hasFieldErrors()) {
            throw ExceptionsGenerator.getException(ExceptionType.INVALID_OBJECT, "Invalid filter parameters");
        }
        return new ResponseEntity<>(
                gameService.getFilteredGames(filter),
                HttpStatus.OK);
    }

    @PutMapping("/{gameId}/state")
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
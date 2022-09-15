package com.springchallenge.gamebackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.springchallenge.gamebackend.dto.input.gamestate.GameStateDto;
import com.springchallenge.gamebackend.dto.output.game.GameDto;
import com.springchallenge.gamebackend.service.game.GameService;
import com.springchallenge.gamebackend.service.gamestate.GameStateService;

@RestController
@RequestMapping("/games")
public class GameController {

    @Autowired
    private GameService gameService;
    @Autowired
    private GameStateService gameStateService;

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

    @PostMapping("/{gameId}/state")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setGameState(@PathVariable String gameId, @RequestBody GameStateDto state) {
        gameStateService.setGameState("8a2618aa-af26-41ec-8263-c8bf69b8da5f", gameId, state);
    }
}

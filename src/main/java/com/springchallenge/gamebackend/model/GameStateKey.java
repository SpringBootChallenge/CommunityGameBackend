package com.springchallenge.gamebackend.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class GameStateKey implements Serializable {
    @Column(name = "game_id")
    private String gameId;
    @Column(name = "user_id")
    private String userId;

    public GameStateKey(String gameId, String userId) {
        this.gameId = gameId;
        this.userId = userId;
    }

    public GameStateKey() {
    }

}
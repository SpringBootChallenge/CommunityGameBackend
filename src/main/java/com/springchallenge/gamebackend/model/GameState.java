package com.springchallenge.gamebackend.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="game_state")
@Data
public class GameState {
    @EmbeddedId
    GameStateKey id;

    @ManyToOne
    @MapsId("gameId")
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="state", foreignKey = @ForeignKey(name="FK_GAME_STATE"))
    private State state;
}

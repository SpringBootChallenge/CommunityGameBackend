package com.springchallenge.gamebackend.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

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

@Embeddable
@Data
class GameStateKey implements Serializable{
    @Column(name = "game_id")
    private String gameId;
    @Column(name = "user_id")
    private String userId;
}

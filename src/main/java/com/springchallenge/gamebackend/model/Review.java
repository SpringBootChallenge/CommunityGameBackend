package com.springchallenge.gamebackend.model;


import lombok.Data;

import java.util.UUID;
import javax.persistence.*;
import java.time.LocalDateTime;

import com.springchallenge.gamebackend.dto.input.review.ReviewDto;

@Entity
@Table(name="review")
@Data
public class Review {
    @Id
    @Column(name="id")
    private String id;
    @Column(name="text")
    private String text;
    @Column(name="score")
    private int score;
    @Column(name="timestamp")
    private LocalDateTime timeStamp;

    @ManyToOne
    @JoinColumn(name = "game_id", foreignKey = @ForeignKey(name = "FK_REVIEW_GAME" ))
    private Game game;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_REVIEW_USER" ))
    private User user;

    public Review(){
        this.id = UUID.randomUUID().toString();
        this.timeStamp = LocalDateTime.now();
    }

    public Review(String text, int score) {
        this();
        this.text = text;
        this.score = score;
    }

    public Review (ReviewDto reviewDto, User user, Game game){
        this(reviewDto.getText(), reviewDto.getScore());
        this.user = user;
        this.game = game;
    }

}

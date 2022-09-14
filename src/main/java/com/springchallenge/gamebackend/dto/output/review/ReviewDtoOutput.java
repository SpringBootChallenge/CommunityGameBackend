package com.springchallenge.gamebackend.dto.output.review;


import com.springchallenge.gamebackend.dto.output.game.GameDtoOutput;
import lombok.Data;
import java.time.LocalDateTime;
import com.springchallenge.gamebackend.dto.output.game.GameDto;
import com.springchallenge.gamebackend.dto.output.user.UserDto;

@Data
public class ReviewDtoOutput {
    private String id;
    private int score;
    private String text;
    private LocalDateTime timeStamp;
    private GameDtoOutput game;
    private UserDto user;
}

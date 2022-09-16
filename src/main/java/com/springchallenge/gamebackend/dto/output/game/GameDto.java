package com.springchallenge.gamebackend.dto.output.game;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.springchallenge.gamebackend.dto.output.user.UserDto;

import lombok.Data;

@Data
public class GameDto {
    private String id;
    private String title;
    private String platform;
    private LocalDate releaseDate;
    private String description;
    private String genre;
    private String image;
    private LocalDateTime updatedAt;
    private int backlogCount;
    private int playingCount;
    private int beatCount;
    private int retiredCount;
    private UserDto updatedBy;
}



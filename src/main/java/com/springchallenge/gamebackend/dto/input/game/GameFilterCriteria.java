package com.springchallenge.gamebackend.dto.input.game;

import lombok.Data;

@Data
public class GameFilterCriteria {
    private Integer page = 1;
    private Integer limit = 10;
    private String sort = "newest";
    private String title;
    private String platform;
    private String genre;

}

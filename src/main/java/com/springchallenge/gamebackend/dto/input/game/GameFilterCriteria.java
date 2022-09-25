package com.springchallenge.gamebackend.dto.input.game;

import java.io.Serializable;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class GameFilterCriteria implements Serializable {
    @Min(1)
    private Integer page = 1;
    @Min(1)
    private Integer limit = 10;
    private SortGameFilter sort = SortGameFilter.newest;
    private String title;
    private String platform;
    private String genre;

}

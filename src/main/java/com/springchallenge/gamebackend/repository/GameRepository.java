package com.springchallenge.gamebackend.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.springchallenge.gamebackend.model.Game;

public interface GameRepository extends PagingAndSortingRepository<Game, String> {

}

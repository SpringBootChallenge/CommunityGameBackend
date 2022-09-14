package com.springchallenge.gamebackend.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.springchallenge.gamebackend.model.Game;

public interface GameRepository extends PagingAndSortingRepository<Game, String> {

    @Query(value = "SELECT count(g.id) FROM game as g INNER JOIN game_state as gs ON g.id=gs.game_id INNER JOIN state as s ON gs.state=s.id WHERE s.description=?1", nativeQuery = true)
    int countByState(String state);

    List<Game> findByPlatformOrGenreOrTitleLikeIgnoreCase(String platform, String genre, String title,
            Pageable pagination);

}

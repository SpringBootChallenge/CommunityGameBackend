package com.springchallenge.gamebackend.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.springchallenge.gamebackend.model.Game;

public interface GameRepository extends PagingAndSortingRepository<Game, String> {

        public String FILTER_CONDITION = "WHERE (?1 IS NULL OR g.platform=?1) AND (?2 IS NULL OR g.genre=?2) AND (?3 IS NULL OR UPPER(g.title) LIKE CONCAT('%',UPPER(?3),'%'))";

        @Query(value = "SELECT COUNT(g.id) FROM game AS g "
                        + "INNER JOIN game_state AS gs ON g.id=gs.game_id "
                        + "INNER JOIN state AS s ON gs.state=s.id "
                        + "WHERE g.id=?1 AND s.description=?2", nativeQuery = true)
        int countByState(String gameId, String state);

        @Query(value = "SELECT * FROM game g "
                        + FILTER_CONDITION
                        + " ORDER BY g.update_at DESC", countQuery = "SELECT COUNT(g.id) FROM game g"
                                        + FILTER_CONDITION, nativeQuery = true)
        List<Game> findByFiltersOrderByUpdatedAt(String platform, String genre, String title,
                        Pageable pagination);

        @Query(value = "SELECT g.*, COUNT(CASE s.description WHEN ?4 THEN 1 ELSE NULL END) AS numPlayers FROM game g "
                        + "LEFT JOIN game_state gs ON g.id=gs.game_id "
                        + "LEFT JOIN state s ON gs.state=s.id "
                        + FILTER_CONDITION
                        + " GROUP BY g.id ORDER BY numPlayers DESC", countQuery = "SELECT COUNT(g.id) FROM game g "
                                        + "LEFT JOIN game_state gs ON g.id=gs.game_id "
                                        + "LEFT JOIN state s ON gs.state=s.id "
                                        + FILTER_CONDITION
                                        + " GROUP BY g.id", nativeQuery = true)
        List<Game> findByFiltersOrderByPlayers(String platform, String genre, String title, String gameState,
                        Pageable pagination);

        @Query(value = "SELECT g.*, AVG(r.score) AS score FROM game g "
                        + "LEFT JOIN review r ON g.id=r.game_id "
                        + FILTER_CONDITION
                        + " GROUP BY g.id ORDER BY score DESC", countQuery = "SELECT COUNT(g.id) FROM game g "
                                        + "LEFT JOIN review r ON g.id=r.game_id "
                                        + FILTER_CONDITION + " GROUP BY g.id", nativeQuery = true)
        List<Game> findByFiltersOrderByScore(String platform, String genre, String title,
                        Pageable pagination);

}

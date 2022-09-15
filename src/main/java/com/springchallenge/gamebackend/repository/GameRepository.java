package com.springchallenge.gamebackend.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.springchallenge.gamebackend.model.Game;

public interface GameRepository extends PagingAndSortingRepository<Game, String> {

    public String FILTER_CONDITION = "where (?1 is null or g.platform=?1) and (?2 is null or g.genre=?2) and (?3 is null or upper(g.title) like concat('%',upper(?3),'%'))";

    @Query(value = "SELECT count(g.id) FROM game as g INNER JOIN game_state as gs ON g.id=gs.game_id INNER JOIN state as s ON gs.state=s.id WHERE g.id=?1 and s.description=?2", nativeQuery = true)
    int countByState(String gameId, String state);

    List<Game> findByPlatformOrGenreOrTitleLikeIgnoreCase(String platform, String genre, String title,
            Pageable pagination);

    @Query(value = "SELECT * from game g " + FILTER_CONDITION
            + " order by g.update_at desc", countQuery = "SELECT count(g.id) from game g"
                    + FILTER_CONDITION, nativeQuery = true)
    List<Game> findByFiltersOrderByUpdatedAt(String platform, String genre, String title,
            Pageable pagination);

    @Query(value = "select g.*, count(g.id) as numPlayers from game g inner join game_state gs on g.id=gs.game_id inner join state s on gs.state=s.id "
            + FILTER_CONDITION
            + " and s.description=?4 group by g.id order by numPlayers desc", countQuery = "select count(g.id) from game g inner join game_state gs on g.id=gs.game_id inner join state s on gs.state=s.id "
                    + FILTER_CONDITION
                    + " and s.description=?4", nativeQuery = true)
    List<Game> findByFiltersOrderByPlayers(String platform, String genre, String title, String gameState,
            Pageable pagination);

    @Query(value = "select g.*, avg(r.score) as score from game g left join review r on g.id=r.game_id "
            + FILTER_CONDITION
            + " group by g.id order by score desc", countQuery = "SELECT count(g.id) from game g left join review r on g.id=r.game_id "
                    + FILTER_CONDITION + " group by g.id", nativeQuery = true)
    List<Game> findByFiltersOrderByScore(String platform, String genre, String title,
            Pageable pagination);

}

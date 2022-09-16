package com.springchallenge.gamebackend.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.springchallenge.gamebackend.model.Review;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

@Repository
public interface ReviewRepository extends PagingAndSortingRepository<Review, String> {
    public String FILTER_CONDITION = "WHERE (?1 IS NULL OR r.game_id=?1) AND (?2 IS NULL OR r.user_id=?2)";

    @Query(value = "SELECT * FROM review as r" + FILTER_CONDITION)
    List<Review> findByFilter(String gameId, String userId, Pageable pageable);

    Review findByGameIdAndUserId(String gameId, String userId);

    Review findByUserIdAndId(String userId, String id);

    void deleteById(String id);
}

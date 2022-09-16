package com.springchallenge.gamebackend.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import com.springchallenge.gamebackend.model.Review;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

@Repository
public interface ReviewRepository extends PagingAndSortingRepository<Review, String> {

    @Query(value = "SELECT * FROM review as r WHERE (?1 IS NULL OR r.game_id=?1) AND (?2 IS NULL OR r.user_id=?2)" , nativeQuery = true)
    List<Review> findByFilter(String gameId, String userId, Pageable pageable);

    Review findByGameIdAndUserId(String gameId, String userId);

    Review findByUserIdAndId(String userId, String id);

    void deleteById(String id);
}

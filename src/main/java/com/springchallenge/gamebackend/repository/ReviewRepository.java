package com.springchallenge.gamebackend.repository;

import org.springframework.stereotype.Repository;
import com.springchallenge.gamebackend.model.Review;
import org.springframework.data.repository.PagingAndSortingRepository;

@Repository
public interface ReviewRepository extends PagingAndSortingRepository<Review, String> {
    Review findByGameIdAndUserId(String gameId, String userId);
}

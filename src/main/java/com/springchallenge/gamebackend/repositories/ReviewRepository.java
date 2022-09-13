package com.springchallenge.gamebackend.repositories;

import com.springchallenge.gamebackend.model.Review;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReviewRepository extends PagingAndSortingRepository<Review, String> {

}

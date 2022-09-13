package com.springchallenge.gamebackend.repositories;

import com.springchallenge.gamebackend.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, String> {
}

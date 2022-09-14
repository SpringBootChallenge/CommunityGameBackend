package com.springchallenge.gamebackend.repository;

import com.springchallenge.gamebackend.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, String> {
    User findByEmail(String email);

    User findByUsername(String username);
}

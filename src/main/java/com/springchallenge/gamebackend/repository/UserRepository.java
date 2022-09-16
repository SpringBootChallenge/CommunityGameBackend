package com.springchallenge.gamebackend.repository;

import org.springframework.stereotype.Repository;
import com.springchallenge.gamebackend.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;


@Repository
public interface UserRepository extends PagingAndSortingRepository<User, String> {
    User findByEmail(String email);

    User findByUsername(String username);

}

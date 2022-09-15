package com.springchallenge.gamebackend.service.user;

import java.util.Optional;

import com.springchallenge.gamebackend.model.User;

public interface UserService {

    public User createUser(User user);

    public User findById(String id);

    public Optional<User> findPossibleUserById(String id);

}
package com.springchallenge.gamebackend.service.user;

import com.springchallenge.gamebackend.model.User;


public interface UserService {

    User createUser(User user);

    User findById(String id);
}
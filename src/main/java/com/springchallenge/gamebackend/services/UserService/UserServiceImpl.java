package com.springchallenge.gamebackend.services.UserService;

import com.springchallenge.gamebackend.model.User;
import com.springchallenge.gamebackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        userRepository.save(user);
        return user;
    }
}

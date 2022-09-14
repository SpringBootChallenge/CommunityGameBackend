package com.springchallenge.gamebackend.services.UserService;

import com.springchallenge.gamebackend.exception.ExceptionType;
import com.springchallenge.gamebackend.exception.ExceptionsGenerator;
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
        Boolean userByEmail= userRepository.findByEmail(user.getEmail()) == null;
        Boolean userByUsername= userRepository.findByUsername(user.getUsername()) == null;
        if(userByEmail && userByUsername){
            userRepository.save(user);
            return user;
        }else{
            throw ExceptionsGenerator.getException(ExceptionType.DUPLICATE_ENTITY, "Email or username in use.");
        }
    }
}

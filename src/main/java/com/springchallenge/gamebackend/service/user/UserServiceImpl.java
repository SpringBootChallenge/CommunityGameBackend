package com.springchallenge.gamebackend.service.user;

import org.springframework.stereotype.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.springchallenge.gamebackend.model.User;
import com.springchallenge.gamebackend.exception.ExceptionType;
import com.springchallenge.gamebackend.repository.UserRepository;
import com.springchallenge.gamebackend.exception.ExceptionsGenerator;

@Service
public class UserServiceImpl implements UserService {

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

    @Override
    public User findById(String id) {
        Optional<User> possibleUser = findPossibleUserById(id);
        if (possibleUser.isPresent()) {
            return possibleUser.get();
        }
        throw ExceptionsGenerator.getException(ExceptionType.NOT_FOUND, "There is no user with the supplied id");
    }

    @Override
    public Optional<User> findPossibleUserById(String id) {
        return userRepository.findById(id);
    }
}
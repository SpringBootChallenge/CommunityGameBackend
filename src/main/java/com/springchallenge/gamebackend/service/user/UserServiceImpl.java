package com.springchallenge.gamebackend.service.user;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.springchallenge.gamebackend.model.User;
import com.springchallenge.gamebackend.dto.input.user.UserDto;
import com.springchallenge.gamebackend.exception.ExceptionType;
import com.springchallenge.gamebackend.repository.UserRepository;
import com.springchallenge.gamebackend.dto.input.user.UserLoginDto;
import com.springchallenge.gamebackend.dto.output.user.UserDtoSignUp;
import com.springchallenge.gamebackend.exception.ExceptionsGenerator;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    public UserServiceImpl (@Autowired UserRepository userRepository, @Autowired ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDtoSignUp createUser(UserDto userDto) {
        User user = new User(userDto);
        Boolean userByEmail = userRepository.findByEmail(user.getEmail()) == null;
        Boolean userByUsername = userRepository.findByUsername(user.getUsername()) == null;
        if (userByEmail && userByUsername) {
            userRepository.save(user);
            return modelMapper.map(user, UserDtoSignUp.class);
        }
        throw ExceptionsGenerator.getException(ExceptionType.DUPLICATE_ENTITY, "Email or username in use.");
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
    public UserDtoSignUp loginUser(UserLoginDto userLoginDto) {
        String username = userLoginDto.getUsername();
        User userExists = userRepository.findByUsername(username);
        if (userExists != null) {
            if (reviewPassword(userExists.getPassword(), userLoginDto.getPassword())) {
                userExists.login();
                userRepository.save(userExists);
                return modelMapper.map(userExists, UserDtoSignUp.class);
            }
            throw ExceptionsGenerator.getException(ExceptionType.INVALID_CREDENTIALS,
                    "Invalid credentials");
        }
        throw ExceptionsGenerator.getException(ExceptionType.NOT_FOUND,
                "There is no user with this username.");
    }

    public boolean isLogged(String userId) {
        User user = findById(userId);
        return user.isLogged();
    }

    private boolean reviewPassword(String dbPassword, String userPassword) {
        return dbPassword.equals(userPassword);
    }

    @Override
    public void logoutUser(String userId) {
        User user = findById(userId);
        user.logout();
        userRepository.save(user);
    }

    @Override
    public Optional<User> findPossibleUserById(String id) {
        return userRepository.findById(id);
    }
}
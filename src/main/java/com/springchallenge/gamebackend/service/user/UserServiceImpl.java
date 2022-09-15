package com.springchallenge.gamebackend.service.user;

import com.springchallenge.gamebackend.dto.input.user.UserDto;
import com.springchallenge.gamebackend.dto.output.user.UserDtoSignUp;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.springchallenge.gamebackend.model.User;
import com.springchallenge.gamebackend.exception.ExceptionType;
import com.springchallenge.gamebackend.repository.UserRepository;
import com.springchallenge.gamebackend.dto.input.user.UserLoginDto;
import com.springchallenge.gamebackend.exception.ExceptionsGenerator;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDtoSignUp createUser(UserDto userDto) {
        User user = new User(userDto);
        Boolean userByEmail = userRepository.findByEmail(user.getEmail()) == null;
        Boolean userByUsername = userRepository.findByUsername(user.getUsername()) == null;
        if(userByEmail && userByUsername){
            userRepository.save(user);
            ModelMapper modelMapper = new ModelMapper();
            UserDtoSignUp userDtoSignUp = modelMapper.map(user, UserDtoSignUp.class);
            return userDtoSignUp;
        }
        throw ExceptionsGenerator.getException(ExceptionType.DUPLICATE_ENTITY, "Email or username in use.");
    }

    @Override
    public User findById(String id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return userRepository.findById(id).get();
        }
        throw ExceptionsGenerator.getException(ExceptionType.NOT_FOUND,
                "There is no user with the supplied id.");
    }

    @Override
    public UserDtoSignUp loginUser(UserLoginDto userLoginDto) {
        String username = userLoginDto.getUsername();
        User userExists = userRepository.findByUsername(username);
        if(userExists != null){
            if(reviewPassword(userExists.getPassword(), userLoginDto.getPassword())){
                userExists.login();
                userRepository.save(userExists);
                ModelMapper modelMapper = new ModelMapper();
                UserDtoSignUp userDtoSignUp = modelMapper.map(userExists, UserDtoSignUp.class);
                return userDtoSignUp;
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

    private boolean reviewPassword(String dbPassword, String userPassword){
        return dbPassword.equals(userPassword);
    }

    @Override
    public void logoutUser(String userId) {
        User user = findById(userId);
        user.logout();
        userRepository.save(user);
    }
}

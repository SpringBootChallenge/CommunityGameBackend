package com.springchallenge.gamebackend.service.user;

import com.springchallenge.gamebackend.dto.input.user.UserDto;
import com.springchallenge.gamebackend.model.User;
import com.springchallenge.gamebackend.dto.input.user.UserLoginDto;
import com.springchallenge.gamebackend.dto.output.user.UserDtoSignUp;

public interface UserService {

    User findById(String id);

    UserDtoSignUp createUser(UserDto userDto);

    UserDtoSignUp loginUser(UserLoginDto userLoginDto);

    boolean isLogged(String userId);

    void logoutUser(String userId);
}
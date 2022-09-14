package com.springchallenge.gamebackend.controller;

import com.springchallenge.gamebackend.dto.input.user.UserDto;
import com.springchallenge.gamebackend.dto.output.user.UserDtoSignUp;
import com.springchallenge.gamebackend.model.User;
import com.springchallenge.gamebackend.service.user.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserDtoSignUp> createUser(@RequestBody UserDto userDto){
        User user = new User(userDto);
        userService.createUser(user);
        ModelMapper modelMapper = new ModelMapper();
        UserDtoSignUp userDtoSignUp = modelMapper.map(user, UserDtoSignUp.class);
        return new ResponseEntity<>(userDtoSignUp, HttpStatus.CREATED);
    }
}

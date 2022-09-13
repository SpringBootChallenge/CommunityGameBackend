package com.springchallenge.gamebackend.controllers;

import com.springchallenge.gamebackend.dto.input.user.UserDto;
import com.springchallenge.gamebackend.dto.output.user.UserDtoSignUp;
import com.springchallenge.gamebackend.model.User;
import com.springchallenge.gamebackend.services.UserService.UserService;
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

    @GetMapping("/health-check")
    public ResponseEntity<String> healthCheck(){
        return ResponseEntity.ok("OK");
    }

    @PostMapping
    public ResponseEntity<UserDtoSignUp> createUser(@RequestBody UserDto userDto){
        System.out.println("Create User");
        User user = new User(userDto);
        System.out.println(user.getId());
        userService.createUser(user);
        ModelMapper modelMapper = new ModelMapper();
        UserDtoSignUp userDtoSignUp = modelMapper.map(user, UserDtoSignUp.class);
        return new ResponseEntity<>(userDtoSignUp, HttpStatus.CREATED);
    }
}

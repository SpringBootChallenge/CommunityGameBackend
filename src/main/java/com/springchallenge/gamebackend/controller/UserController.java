package com.springchallenge.gamebackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.beans.factory.annotation.Autowired;

import com.springchallenge.gamebackend.dto.input.user.UserDto;
import com.springchallenge.gamebackend.service.user.UserService;
import com.springchallenge.gamebackend.exception.ExceptionType;
import com.springchallenge.gamebackend.dto.input.user.UserLoginDto;
import com.springchallenge.gamebackend.exception.ExceptionsGenerator;
import com.springchallenge.gamebackend.dto.output.user.UserDtoSignUp;

import javax.validation.Valid;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserDtoSignUp> createUser(@RequestBody @Valid UserDto userDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()) throw ExceptionsGenerator.getException(ExceptionType.INVALID_OBJECT, "Incorrectly formed request");
        UserDtoSignUp userDtoSignUp= userService.createUser(userDto);
        return new ResponseEntity<>(userDtoSignUp, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public  ResponseEntity<UserDtoSignUp> loginUser(@RequestBody UserLoginDto userLoginDto){
        UserDtoSignUp userDtoSignUp = userService.loginUser(userLoginDto);
        return new ResponseEntity<>(userDtoSignUp, HttpStatus.OK);
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logoutUser(@RequestHeader(value = "User-id", required = true) String optionalHeader) {
        userService.logoutUser(optionalHeader);
    }
}

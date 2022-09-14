package com.springchallenge.gamebackend.dto.input.user;


import lombok.Data;

@Data
public class UserDto {
    private String id;
    private String email;
    private String username;
    private String password;

}

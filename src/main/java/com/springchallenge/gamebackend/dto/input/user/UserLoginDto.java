package com.springchallenge.gamebackend.dto.input.user;

import lombok.Data;

@Data
public class UserLoginDto {
    private String username;
    private String password;
}

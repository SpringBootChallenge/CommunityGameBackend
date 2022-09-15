package com.springchallenge.gamebackend.dto.input.user;


import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class UserDto {
    @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,15}$")
    private String email;
    private String username;
    private String password;
}

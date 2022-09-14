package com.springchallenge.gamebackend.exception;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ExceptionResponse {
    private LocalDateTime timeStamp;
    private String message;

    public ExceptionResponse() {
        timeStamp = LocalDateTime.now();
    }
}

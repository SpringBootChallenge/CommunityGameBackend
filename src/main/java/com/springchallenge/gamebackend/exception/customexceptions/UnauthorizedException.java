package com.springchallenge.gamebackend.exception.customexceptions;


public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}

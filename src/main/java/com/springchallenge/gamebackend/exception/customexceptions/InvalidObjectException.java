package com.springchallenge.gamebackend.exception.customexceptions;

public class InvalidObjectException extends RuntimeException {

    public InvalidObjectException(String message) {
        super(message);
    }

}

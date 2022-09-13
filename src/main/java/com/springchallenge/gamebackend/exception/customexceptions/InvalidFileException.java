package com.springchallenge.gamebackend.exception.customexceptions;

public class InvalidFileException extends RuntimeException {

    public InvalidFileException(String message) {
        super(message);
    }

}

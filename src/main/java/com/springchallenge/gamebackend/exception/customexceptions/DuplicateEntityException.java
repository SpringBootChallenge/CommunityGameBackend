package com.springchallenge.gamebackend.exception.customexceptions;

public class DuplicateEntityException extends RuntimeException {

    public DuplicateEntityException(String message) {
        super(message);
    }

}

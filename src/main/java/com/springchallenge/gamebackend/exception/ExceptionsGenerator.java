package com.springchallenge.gamebackend.exception;

import org.springframework.stereotype.Component;

import com.springchallenge.gamebackend.exception.customexceptions.DuplicateEntityException;
import com.springchallenge.gamebackend.exception.customexceptions.InvalidCredentialsException;
import com.springchallenge.gamebackend.exception.customexceptions.InvalidFileException;
import com.springchallenge.gamebackend.exception.customexceptions.InvalidObjectException;
import com.springchallenge.gamebackend.exception.customexceptions.ObjectNotFoundException;

@Component
public class ExceptionsGenerator {

    public static RuntimeException getException(ExceptionType type, String message) {
        switch (type) {
            case NOT_FOUND:
                return new ObjectNotFoundException(message);
            case INVALID_CREDENTIALS:
                return new InvalidCredentialsException(message);
            case DUPLICATE_ENTITY:
                return new DuplicateEntityException(message);
            case INVALID_OBJECT:
                return new InvalidObjectException(message);
            case INVALID_FILE:
                return new InvalidFileException(message);
            default:
                return new RuntimeException(message);
        }
    }
}

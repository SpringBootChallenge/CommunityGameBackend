package com.springchallenge.gamebackend.exception;

import com.springchallenge.gamebackend.exception.customexceptions.*;
import org.springframework.stereotype.Component;

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
            case UNAUTHORIZED:
                return new UnauthorizedException(message);
            default:
                return new RuntimeException(message);
        }
    }
}

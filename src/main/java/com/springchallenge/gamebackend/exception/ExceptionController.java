package com.springchallenge.gamebackend.exception;

import com.springchallenge.gamebackend.exception.customexceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@RestController
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundExceptions(ObjectNotFoundException exception,
            WebRequest request) {
        ExceptionResponse response = new ExceptionResponse();
        response.setMessage(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidCredentialsException(InvalidCredentialsException exception,
            WebRequest request) {
        ExceptionResponse response = new ExceptionResponse();
        response.setMessage(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(DuplicateEntityException.class)
    public ResponseEntity<ExceptionResponse> handleDuplicateEntityException(DuplicateEntityException exception,
            WebRequest request) {
        ExceptionResponse response = new ExceptionResponse();
        response.setMessage(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidObjectException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidObjectException(InvalidObjectException exception,
            WebRequest request) {
        ExceptionResponse response = new ExceptionResponse();
        response.setMessage(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidFileException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidFileException(InvalidFileException exception,
            WebRequest request) {
        ExceptionResponse response = new ExceptionResponse();
        response.setMessage(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ExceptionResponse> handleUnauthorizedException(UnauthorizedException exception,
                                                                         WebRequest request) {
        ExceptionResponse response = new ExceptionResponse();
        response.setMessage(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ServletRequestBindingException.class)
    public ResponseEntity<ExceptionResponse> handleHeaderError(ServletRequestBindingException exception,
                                                               WebRequest request){
        ExceptionResponse response = new ExceptionResponse();
        response.setMessage(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

}

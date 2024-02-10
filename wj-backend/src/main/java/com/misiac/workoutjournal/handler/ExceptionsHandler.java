package com.misiac.workoutjournal.handler;

import com.misiac.workoutjournal.exception.EntityAlreadyExistsException;
import com.misiac.workoutjournal.exception.EntityDoesNotExistException;
import com.misiac.workoutjournal.exception.UnauthorizedException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler
    public ResponseEntity<String> handleException(EntityDoesNotExistException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleException(EntityAlreadyExistsException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleException(UnauthorizedException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleException(IllegalArgumentException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException e) {
        StringBuilder sb = new StringBuilder();
        e.getBindingResult().getAllErrors().forEach(err -> sb.append(err.getDefaultMessage()).append("\n"));
        return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<String> constraintViolationException(ConstraintViolationException e) {
        StringBuilder sb = new StringBuilder();
        e.getConstraintViolations().forEach(err -> sb.append(err.getMessage()).append("\n"));
        return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
    }
}

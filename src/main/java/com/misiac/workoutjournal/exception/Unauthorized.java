package com.misiac.workoutjournal.exception;

public class Unauthorized extends SecurityException {
    public Unauthorized(String message) {
        super(message);
    }
}
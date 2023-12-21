package com.misiac.workoutjournal.exception;

public class EntityAlreadyExists extends IllegalArgumentException {

    public EntityAlreadyExists(String message) {
        super(message);
    }
}

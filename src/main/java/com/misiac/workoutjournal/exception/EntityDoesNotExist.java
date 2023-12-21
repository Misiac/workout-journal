package com.misiac.workoutjournal.exception;

public class EntityDoesNotExist extends IllegalArgumentException {

    public EntityDoesNotExist(String message) {
        super(message);
    }
}

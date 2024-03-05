package ovh.workoutjournal.exception;

public class EntityDoesNotExistException extends IllegalArgumentException {

    public EntityDoesNotExistException(String message) {
        super(message);
    }
}

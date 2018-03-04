package net.restapp.exception;

public class EntityAlreadyExistException extends RuntimeException {

    public EntityAlreadyExistException(String exception) {
        super(exception);
    }
}

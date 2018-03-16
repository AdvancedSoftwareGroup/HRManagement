package net.restapp.exception;

/**
 * Handler exception
 */
public class EntityConstraintException extends RuntimeException {

    public EntityConstraintException(String exception) {
        super(exception);
    }
}

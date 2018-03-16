package net.restapp.exception;

/**
 * Handler exception
 */
public class NotEnoughHoursException extends RuntimeException {
    public NotEnoughHoursException(String exception) {
        super(exception);
    }
}

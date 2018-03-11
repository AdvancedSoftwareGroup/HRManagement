package net.restapp.exception;

public class NotEnoughHoursException extends RuntimeException {
    public NotEnoughHoursException(String exception) {
        super(exception);
    }
}

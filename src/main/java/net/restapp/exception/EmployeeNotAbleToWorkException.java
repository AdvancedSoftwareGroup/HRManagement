package net.restapp.exception;

/**
 * Handler exception
 */
public class EmployeeNotAbleToWorkException extends RuntimeException {
    public EmployeeNotAbleToWorkException(String message) {
        super(message);
    }

}

package net.restapp.exception.handler;

import net.restapp.exception.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

/**
 * The class contains total handler exceptions for all rest-layer.
 */

@ControllerAdvice(annotations = RestController.class)
public class RestResponseEntityExceptionHandler {

    /**
     * The method transforms an exception {@link EntityNotFoundException} to the mode of {@link ErrorBody}
     *
     * @param ex      - gotten exception
     * @param request - HTTP request
     * @return response entity
     */
    @ExceptionHandler(value = {EntityNotFoundException.class})
    protected ResponseEntity<ErrorBody> handleNotFound(EntityNotFoundException ex, HttpServletRequest request) {
        ErrorBody errorBody = getErrorBody(ex, request, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorBody, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    /**
     * The method transforms an exception {@link PathVariableNullException} to the mode of {@link ErrorBody}
     *
     * @param ex      - gotten exception
     * @param request - HTTP request
     * @return response entity
     */
    @ExceptionHandler(value = {PathVariableNullException.class})
    protected ResponseEntity<ErrorBody> handlePathVariableNull(PathVariableNullException ex, HttpServletRequest request) {
        ErrorBody errorBody = getErrorBody(ex, request, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorBody, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    /**
     * The method transforms an exception {@link PathVariableNullException} to the mode of {@link ErrorBody}
     *
     * @param ex      - gotten exception
     * @param request - HTTP request
     * @return response entity
     */
    @ExceptionHandler(value = {EntityNullException.class})
    protected ResponseEntity<ErrorBody> handleEntityNull(EntityNullException ex, HttpServletRequest request) {
        ErrorBody errorBody = getErrorBody(ex, request, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorBody, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    /**
     * The method transforms an exception {@link net.restapp.exception.EntityConstraintException} to the mode of {@link ErrorBody}
     *
     * @param ex      - gotten exception
     * @param request - HTTP request
     * @return response entity
     */
    @ExceptionHandler(value = {EntityConstraintException.class})
    protected ResponseEntity<ErrorBody> handleEntityConstraint(EntityConstraintException ex, HttpServletRequest request) {
        ErrorBody errorBody = getErrorBody(ex, request, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorBody, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    /**
     * The method transforms an exception {@link net.restapp.exception.EntityConstraintException} to the mode of {@link ErrorBody}
     *
     * @param ex      - gotten exception
     * @param request - HTTP request
     * @return response entity
     */
    @ExceptionHandler(value = {NotEnoughHoursException.class})
    protected ResponseEntity<ErrorBody> NotEnoughHours(NotEnoughHoursException ex, HttpServletRequest request) {
        ErrorBody errorBody = getErrorBody(ex, request, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorBody, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    /**
     * The method transforms an exception {@link IllegalArgumentException} to the mode of {@link ErrorBody}
     *
     * @param ex      - gotten exception
     * @param request - HTTP request
     * @return response entity
     */
    @ExceptionHandler(value = {IllegalArgumentException.class})
    protected ResponseEntity<ErrorBody> IllegalArgument(IllegalArgumentException ex, HttpServletRequest request) {
        ErrorBody errorBody = getErrorBody(ex, request, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorBody, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    /**
     * The method transforms an exception {@link IllegalArgumentException} to the mode of {@link ErrorBody}
     *
     * @param ex      - gotten exception
     * @param request - HTTP request
     * @return response entity
     */
    @ExceptionHandler(value = {EmployeeNotAbleToWorkException.class})
    protected ResponseEntity<ErrorBody> EmployeeNotAbleToWorkException(EmployeeNotAbleToWorkException ex, HttpServletRequest request) {
        ErrorBody errorBody = getErrorBody(ex, request, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorBody, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    /**
     * The method for transform a message of exception to the mode of {@link ErrorBody}
     *
     * @param ex      - gotten exception
     * @param request - HTTP request
     * @param STATUS  - HTTP STATUS
     * @return - message in a new mode
     */

    private ErrorBody getErrorBody(Exception ex, HttpServletRequest request, HttpStatus STATUS) {
        ErrorBody errorBody = new ErrorBody();
        errorBody.setTimestamp(System.nanoTime());
        errorBody.setError(STATUS.getReasonPhrase());
        errorBody.setStatus(STATUS.value());
        errorBody.setException(ex.getClass().getName());
        errorBody.setMessage(ex.getMessage());
        errorBody.setPath(request.getRequestURI());
        return errorBody;
    }
}

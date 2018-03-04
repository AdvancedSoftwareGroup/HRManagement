package net.restapp.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class Error extends RuntimeException{
    public Error(String exception) {
        super(exception);
    }

}

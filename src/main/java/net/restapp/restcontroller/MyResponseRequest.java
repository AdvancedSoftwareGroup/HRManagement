package net.restapp.restcontroller;

import net.restapp.exception.ErrorBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public class MyResponseRequest {

    private Object object;
    public MyResponseRequest(Object object) {
        this.object=object;
    }

    public ResponseEntity<Object> notFoundRequest(HttpServletRequest request, Long id){
        String message ;
        if (id == null) {
            message = "No "+object.getClass().getSimpleName()+" at the database";
        } else {
            message = "Not found element with id="+id;
        }

        ErrorBody errorBody = new ErrorBody(
                HttpStatus.NOT_FOUND,
                null,
                message,
                request);
        return new ResponseEntity<>(errorBody, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Object> bedRequest(HttpServletRequest request, String message){
        ErrorBody errorBody = new ErrorBody(
                HttpStatus.BAD_REQUEST,
               null,
                message,
                request);
        return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
    }

}

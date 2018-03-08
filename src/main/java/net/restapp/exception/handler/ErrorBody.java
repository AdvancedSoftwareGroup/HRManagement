package net.restapp.exception.handler;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;

@Data
@NoArgsConstructor
public class ErrorBody {

    private Long timestamp;
    private int status;
    private String error;
    private String exception;
    private String message;
    private String path;


}

package net.restapp.exception;

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

    public ErrorBody(HttpStatus status, Class exceptionClass, String message, HttpServletRequest request) {

        this.timestamp = System.nanoTime();
        this.status = status.value();
        this.error = status.getReasonPhrase();
        if (exceptionClass != null) {
            this.exception = exceptionClass.getName();
        }
        this.message = message;
        this.path = request.getRequestURI();
    }


}

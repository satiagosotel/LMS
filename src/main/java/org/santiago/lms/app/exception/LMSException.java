package org.santiago.lms.app.exception;

import org.springframework.http.HttpStatus;

public class LMSException extends RuntimeException {
    HttpStatus status;

    public LMSException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

}

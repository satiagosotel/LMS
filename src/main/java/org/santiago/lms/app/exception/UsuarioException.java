package org.santiago.lms.app.exception;

import org.springframework.http.HttpStatus;

public class UsuarioException extends RuntimeException {
    HttpStatus status;
    public UsuarioException() {}

    public UsuarioException(String message,HttpStatus status) {
        super(message);
        this.status = status;
    }
}

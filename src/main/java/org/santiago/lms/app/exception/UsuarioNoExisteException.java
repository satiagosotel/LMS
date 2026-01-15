package org.santiago.lms.app.exception;

public class UsuarioNoExisteException extends RuntimeException{
    private String message;

    public UsuarioNoExisteException (){};

    public UsuarioNoExisteException(String msg){
        super(msg);
        this.message = msg;
    }
}

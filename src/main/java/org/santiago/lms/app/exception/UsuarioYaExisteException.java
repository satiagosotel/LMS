package org.santiago.lms.app.exception;

public class UsuarioYaExisteException extends RuntimeException{

    private String message;

    public UsuarioYaExisteException (){};

    public UsuarioYaExisteException(String msg){
        super(msg);
        this.message = msg;
    }
}

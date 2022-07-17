package com.pauloeduardocosta.forum.service.exception;

public class UsernameJaUtilizadoException extends RuntimeException {

    public UsernameJaUtilizadoException(String mensagem) {
        super(mensagem);
    }

    public UsernameJaUtilizadoException(String mensagem, Throwable cause) {
        super(mensagem, cause);
    }
}

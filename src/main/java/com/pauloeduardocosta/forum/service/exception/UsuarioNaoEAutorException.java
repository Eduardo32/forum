package com.pauloeduardocosta.forum.service.exception;

public class UsuarioNaoEAutorException extends RuntimeException {

    public UsuarioNaoEAutorException(String mensagem) {
        super(mensagem);
    }

    public UsuarioNaoEAutorException(String mensagem, Throwable cause) {
        super(mensagem, cause);
    }
}

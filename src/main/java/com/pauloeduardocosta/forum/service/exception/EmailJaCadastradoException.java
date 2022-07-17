package com.pauloeduardocosta.forum.service.exception;

public class EmailJaCadastradoException extends RuntimeException {

    public EmailJaCadastradoException(String mensagem) {
        super(mensagem);
    }

    public EmailJaCadastradoException(String mensagem, Throwable cause) {
        super(mensagem, cause);
    }
}

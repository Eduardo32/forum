package com.pauloeduardocosta.forum.service.exception;

public class TopicoFechadoException extends RuntimeException {

    public TopicoFechadoException(String mensagem) {
        super(mensagem);
    }

    public TopicoFechadoException(String mensagem, Throwable cause) {
        super(mensagem, cause);
    }
}

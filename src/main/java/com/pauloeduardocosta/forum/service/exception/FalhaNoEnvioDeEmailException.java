package com.pauloeduardocosta.forum.service.exception;

public class FalhaNoEnvioDeEmailException extends RuntimeException {

    public FalhaNoEnvioDeEmailException(String mensagem) {
        super(mensagem);
    }

    public FalhaNoEnvioDeEmailException(String mensagem, Throwable cause) {
        super(mensagem, cause);
    }
}

package com.pauloeduardocosta.forum.service.exception;

public class ContaNaoExisteException extends RuntimeException {

    public ContaNaoExisteException(String mensagem) {
        super(mensagem);
    }

    public ContaNaoExisteException(String mensagem, Throwable cause) {
        super(mensagem, cause);
    }
}

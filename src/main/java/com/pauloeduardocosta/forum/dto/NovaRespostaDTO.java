package com.pauloeduardocosta.forum.dto;

public class NovaRespostaDTO {

    private String mensagem;
    private Long topicoId;
    private Long autorId;

    public String getMensagem() {
        return mensagem;
    }

    public Long getTopicoId() {
        return topicoId;
    }

    public Long getAutorId() {
        return autorId;
    }
}

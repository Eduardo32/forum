package com.pauloeduardocosta.forum.dto;

import com.pauloeduardocosta.forum.model.Resposta;

import java.time.LocalDateTime;

public class RespostaDTO {
    private Long id;
    private String mensagem;
    private LocalDateTime dataCriacao;
    private UsuarioDTO autor;
    private Boolean solucao;

    public RespostaDTO(Resposta resposta) {
        this.id = resposta.getId();
        this.mensagem = resposta.getMensagem();
        this.dataCriacao = resposta.getDataCriacao();
        this.autor = new UsuarioDTO(resposta.getAutor());
        this.solucao = resposta.isSolucao();
    }

    public Long getId() {
        return id;
    }

    public String getMensagem() {
        return mensagem;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public UsuarioDTO getAutor() {
        return autor;
    }

    public Boolean getSolucao() {
        return solucao;
    }
}

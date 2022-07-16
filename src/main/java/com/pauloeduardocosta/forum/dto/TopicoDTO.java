package com.pauloeduardocosta.forum.dto;


import com.pauloeduardocosta.forum.model.Topico;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

public class TopicoDTO {
    private Long id;
    private String titulo;
    private String mensagem;
    private LocalDateTime dataCriacao;
    private UsuarioDTO autor;
    private String status;
    private Integer qtdRespostas;

    public TopicoDTO(Topico topico) {
        this.id = topico.getId();
        this.titulo = topico.getTitulo();
        this.mensagem = topico.getMensagem();
        this.dataCriacao = topico.getDataCriacao();
        this.autor = topico.getAutor() != null ? new UsuarioDTO(topico.getAutor()) : null;
        this.status = topico.getStatus().toString();
        this.qtdRespostas = topico.getRespostas().size();
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
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

    public String getStatus() {
        return status;
    }

    public Integer getQtdRespostas() {
        return qtdRespostas;
    }

    public static Page<TopicoDTO> converter(Page<Topico> topicos) {
        return topicos.map(TopicoDTO::new);
    }
}

package com.pauloeduardocosta.forum.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class NovoTopicoDTO {

    @NotBlank
    @Length(min = 5)
    private String titulo;

    @NotBlank
    @Length(min = 10)
    private String mensagem;

    @NotNull
    private Long idUsuario;

    public String getTitulo() {
        return titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }
}

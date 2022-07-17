package com.pauloeduardocosta.forum.dto;

import com.pauloeduardocosta.forum.model.Resposta;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
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
        this.solucao = resposta.getSolucao();
    }
}

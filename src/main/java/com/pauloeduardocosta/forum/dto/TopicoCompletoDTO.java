package com.pauloeduardocosta.forum.dto;

import com.pauloeduardocosta.forum.model.Topico;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class TopicoCompletoDTO {

    private Long id;
    private String titulo;
    private String mensagem;
    private LocalDateTime dataCriacao;
    private UsuarioDTO autor;
    private List<RespostaDTO> respostas = new ArrayList<>();
    private String status;
    private String[] tags;

    public TopicoCompletoDTO(Topico topico) {
        this.id = topico.getId();
        this.titulo = topico.getTitulo();
        this.mensagem = topico.getMensagem();
        this.dataCriacao = topico.getDataCriacao();
        this.autor = topico.getAutor() != null ? new UsuarioDTO(topico.getAutor()) : null;
        this.status = topico.getStatus().toString();
        this.respostas.addAll(topico.getRespostas().stream().map(RespostaDTO::new).collect(Collectors.toList()));
        this.tags = topico.getTags() != null ? topico.getTags().split(", ") : null;
    }
}

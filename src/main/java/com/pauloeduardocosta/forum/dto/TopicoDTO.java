package com.pauloeduardocosta.forum.dto;

import com.pauloeduardocosta.forum.model.Topico;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class TopicoDTO {
    private Long id;
    private String titulo;
    private String mensagem;
    private LocalDateTime dataCriacao;
    private UsuarioDTO autor;
    private String status;
    private Integer qtdRespostas;
    private String[] tags;

    public TopicoDTO(Topico topico) {
        this.id = topico.getId();
        this.titulo = topico.getTitulo();
        this.mensagem = topico.getMensagem();
        this.dataCriacao = topico.getDataCriacao();
        this.autor = topico.getAutor() != null ? new UsuarioDTO(topico.getAutor()) : null;
        this.status = topico.getStatus().toString();
        this.qtdRespostas = topico.getRespostas().size();
        this.tags = topico.getTags() != null ? topico.getTags().split(", ") : null;
    }

    public static Page<TopicoDTO> converter(Page<Topico> topicos) {
        return topicos.map(TopicoDTO::new);
    }
}

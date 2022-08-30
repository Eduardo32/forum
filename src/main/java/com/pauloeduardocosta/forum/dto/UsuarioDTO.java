package com.pauloeduardocosta.forum.dto;

import com.pauloeduardocosta.forum.model.Usuario;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class UsuarioDTO {

    private Long id;
    private String nome;
    private String username;

    public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.username = usuario.getUsername();
    }
}

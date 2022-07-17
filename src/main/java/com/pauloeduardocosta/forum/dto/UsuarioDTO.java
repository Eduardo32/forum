package com.pauloeduardocosta.forum.dto;

import com.pauloeduardocosta.forum.model.Usuario;

import java.util.List;
import java.util.stream.Collectors;

public class UsuarioDTO {

    private Long id;
    private String email;

    public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.email = usuario.getEmail();
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public static List<UsuarioDTO> converter(List<Usuario> usuarios) {
        return usuarios.stream().map(UsuarioDTO::new).collect(Collectors.toList());
    }
}

package com.pauloeduardocosta.forum.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NovoUsuarioDTO {

    private String nome;
    private String username;
    private String email;
    private String senha;
}

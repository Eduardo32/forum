package com.pauloeduardocosta.forum.rs;

import com.pauloeduardocosta.forum.dto.NovoUsuarioDTO;
import com.pauloeduardocosta.forum.dto.UsuarioDTO;
import com.pauloeduardocosta.forum.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioRS {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioDTO> criarNovoUsuario(@Validated @RequestBody NovoUsuarioDTO novoUsuarioDTO) {
        UsuarioDTO usuarioDTO = usuarioService.salvarUsuario(novoUsuarioDTO);
        return ResponseEntity.created(null).body(usuarioDTO);
    }
}

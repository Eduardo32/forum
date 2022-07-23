package com.pauloeduardocosta.forum.rs;

import com.pauloeduardocosta.forum.dto.NovoUsuarioDTO;
import com.pauloeduardocosta.forum.dto.UsuarioDTO;
import com.pauloeduardocosta.forum.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/verificar-conta/{uuid}")
    public ResponseEntity<Object> verificarConta(@PathVariable String uuid) {
        usuarioService.verificarConta(uuid);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/esqueci-minha-senha")
    public ResponseEntity<Object> gerarSenhaTemporaria(@RequestParam String email) {
        usuarioService.gerarSenhaTemporaria(email);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/cancelar-senha-temp")
    public ResponseEntity<Object> cancelarSenhaTemporaria(@PathVariable Long id) {
        usuarioService.cancelarSenhaTemporaria(id);
        return ResponseEntity.ok().build();
    }
}

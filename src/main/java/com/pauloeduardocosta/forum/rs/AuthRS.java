package com.pauloeduardocosta.forum.rs;

import com.pauloeduardocosta.forum.dto.LoginDTO;
import com.pauloeduardocosta.forum.dto.TokenDTO;
import com.pauloeduardocosta.forum.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthRS {

    @Autowired
    private AuthService authService;

    @PostMapping
    public ResponseEntity<TokenDTO> autenticar(@RequestBody LoginDTO loginDTO) {
        try {
            TokenDTO tokenDTO = authService.autenticar(loginDTO);
            return ResponseEntity.ok(tokenDTO);
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }

    }
}

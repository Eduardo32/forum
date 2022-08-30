package com.pauloeduardocosta.forum.service;

import com.pauloeduardocosta.forum.data.UsuarioDetalhes;
import com.pauloeduardocosta.forum.dto.LoginDTO;
import com.pauloeduardocosta.forum.dto.TokenDTO;
import com.pauloeduardocosta.forum.model.Usuario;
import com.pauloeduardocosta.forum.model.enums.ETipoToken;
import org.hibernate.service.spi.InjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenServise tokenService;

    @InjectMocks
    private AuthService authService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = Usuario.builder()
                .id(1L)
                .nome("Usuario")
                .username("usuario")
                .email("usuario@email.com")
                .senha("123456")
                .build();
    }

    @Test
    @DisplayName("Dado que estou me logando com um suario válido")
    void autenticarTeste() {
        LoginDTO loginDTO = new LoginDTO(usuario.getUsername(), usuario.getSenha());
        UsuarioDetalhes usuarioDetalhes = new UsuarioDetalhes(Optional.of(usuario));
        Authentication authenticate = new UsernamePasswordAuthenticationToken(usuario, null, usuarioDetalhes.getAuthorities());
        String token = "token123456";
        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(authenticate);
        when(tokenService.gerarToken(authenticate)).thenReturn(token);
        TokenDTO tokenDTO = authService.autenticar(loginDTO);

        assertNotNull(tokenDTO);
        assertEquals(token, tokenDTO.getToken());
        assertEquals(ETipoToken.BEARER.getDescricao(), tokenDTO.getTipo());
    }
}
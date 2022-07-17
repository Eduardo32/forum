package com.pauloeduardocosta.forum.service;

import com.pauloeduardocosta.forum.dto.LoginDTO;
import com.pauloeduardocosta.forum.dto.TokenDTO;
import com.pauloeduardocosta.forum.model.enums.ETipoToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenServise tokenService;

    public TokenDTO autenticar(LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken dadosLogin = loginDTO.converter();
        Authentication authenticate = authenticationManager.authenticate(dadosLogin);
        String token = tokenService.gerarToken(authenticate);

        return new TokenDTO(token, ETipoToken.BEARER.getDescricao());
    }
}

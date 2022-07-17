package com.pauloeduardocosta.forum.service;

import com.pauloeduardocosta.forum.data.UsuarioDetalhes;
import com.pauloeduardocosta.forum.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenServise {

    @Value("${forum.jwt.expiration}")
    private Long expiration;

    @Value("${forum.jwt.secret}")
    private String secret;

    public String gerarToken(Authentication authenticate) {
        UsuarioDetalhes usuarioDetalhes = (UsuarioDetalhes) authenticate.getPrincipal();
        Usuario usuario = usuarioDetalhes.getUsuario().get();
        Date hoje = new Date();
        Date exp = new Date(hoje.getTime() + expiration);

        return Jwts.builder()
                .setIssuer("API Fórum da Alura")
                .setSubject(usuario.getId().toString())
                .setIssuedAt(hoje)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Boolean isTokenValido(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(this.secret)
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Long getIdUsuario(String token) {
        Claims body = Jwts.parser()
                .setSigningKey(this.secret)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(body.getSubject());
    }
}

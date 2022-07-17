package com.pauloeduardocosta.forum.config.security;

import com.pauloeduardocosta.forum.model.Usuario;
import com.pauloeduardocosta.forum.repository.IUsuarioRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {

    private TokenServise tokenService;
    private IUsuarioRepository usuarioRepository;

    public AutenticacaoViaTokenFilter(TokenServise tokenService, IUsuarioRepository usuarioRepository) {
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = reuperarToken(request);
        Boolean valido = tokenService.isTokenValido(token);
        if(valido) {
            autenticarCliente(token);
        }
        filterChain.doFilter(request, response);
    }

    private void autenticarCliente(String token) {
        Long idUsuario = tokenService.getIdUsuario(token);
        Usuario usuario = usuarioRepository.findById(idUsuario).get();
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String reuperarToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");

        if(authorization == null || authorization.isEmpty() || !authorization.startsWith("Bearer ")) {
            return null;
        }

        return authorization.split(" ")[1];
    }
}

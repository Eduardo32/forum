package com.pauloeduardocosta.forum.data;

import com.pauloeduardocosta.forum.model.SenhaTemporaria;
import com.pauloeduardocosta.forum.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

@Component
public class UsuarioDetalhes implements UserDetails {

    private Optional<Usuario> usuario;

    public UsuarioDetalhes(Optional<Usuario> usuario) {
        this.usuario = usuario;
    }

    public Optional<Usuario> getUsuario() {
        return usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return usuario.orElse(new Usuario()).getPerfis();
    }

    @Override
    public String getPassword() {
        String senha = usuario.orElse(new Usuario()).getSenha();
        String senhaTemporaria = buscarSenhaTemporaria();
        if(senhaTemporaria != null) {
            senha = senhaTemporaria;
        }
        return senha;
    }

    @Override
    public String getUsername() {
        return usuario.orElse(new Usuario()).getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return usuario.orElse(new Usuario()).getEmailVerificado();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    private String buscarSenhaTemporaria() {
        SenhaTemporaria senhaTemporaria = usuario.orElse(new Usuario()).getSenhaTemporaria();
        if(senhaTemporaria != null && validarSenhaTemporaris(senhaTemporaria)) {
            return senhaTemporaria.getSenha();
        }
        return null;
    }

    private boolean validarSenhaTemporaris(SenhaTemporaria senhaTemporaria) {
        LocalDateTime agora = LocalDateTime.now();
        return senhaTemporaria.getDataValidade().isEqual(agora) || senhaTemporaria.getDataValidade().isAfter(agora);
    }
}

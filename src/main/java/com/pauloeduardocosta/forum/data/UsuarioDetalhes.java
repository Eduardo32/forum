package com.pauloeduardocosta.forum.data;

import com.pauloeduardocosta.forum.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Optional;

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
        return usuario.orElse(new Usuario()).getSenha();
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
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

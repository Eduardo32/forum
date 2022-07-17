package com.pauloeduardocosta.forum.service;

import com.pauloeduardocosta.forum.data.UsuarioDetalhes;
import com.pauloeduardocosta.forum.model.Usuario;
import com.pauloeduardocosta.forum.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioDetalhesService implements UserDetailsService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuario = usuarioRepository.findByUsername(username);
        if(usuario.isEmpty()) {
            throw new UsernameNotFoundException("Dados Inválidos!");
        }

        return new UsuarioDetalhes(usuario);
    }
}

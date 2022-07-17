package com.pauloeduardocosta.forum.service;

import com.pauloeduardocosta.forum.dto.NovoUsuarioDTO;
import com.pauloeduardocosta.forum.dto.UsuarioDTO;
import com.pauloeduardocosta.forum.model.Perfil;
import com.pauloeduardocosta.forum.model.Usuario;
import com.pauloeduardocosta.forum.repository.IPerfilRepository;
import com.pauloeduardocosta.forum.repository.IUsuarioRepository;
import com.pauloeduardocosta.forum.service.exception.EmailJaCadastradoException;
import com.pauloeduardocosta.forum.service.exception.UsernameJaUtilizadoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private IPerfilRepository perfilRepository;

    private static final Long ID_PERFIL_USUARIO = 1L;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UsuarioDTO salvarUsuario(NovoUsuarioDTO novoUsuarioDTO) {
        verificarUsernameEEmail(novoUsuarioDTO);
        Usuario usuario = new Usuario(novoUsuarioDTO.getNome(), novoUsuarioDTO.getUsername(), novoUsuarioDTO.getEmail());
        Optional<Perfil> perfil = perfilRepository.findById(ID_PERFIL_USUARIO);
        usuario.setSenha(encoder.encode(novoUsuarioDTO.getSenha()));
        usuario.getPerfis().add(perfil.get());
        usuarioRepository.save(usuario);

        return new UsuarioDTO(usuario);
    }

    private void verificarUsernameEEmail(NovoUsuarioDTO novoUsuarioDTO) {
        Optional<Usuario> usuarioEncontrado;

        usuarioEncontrado = usuarioRepository.findByUsername(novoUsuarioDTO.getUsername());
        if(usuarioEncontrado.isPresent()) {
            throw new UsernameJaUtilizadoException("O username " + novoUsuarioDTO.getUsername() + " já está em uso.");
        }

        usuarioEncontrado = usuarioRepository.findByEmail(novoUsuarioDTO.getEmail());
        if(usuarioEncontrado.isPresent()) {
            throw new EmailJaCadastradoException("O email " + novoUsuarioDTO.getEmail() + " já está cadastrado.");
        }
    }
}

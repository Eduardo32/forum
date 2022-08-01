package com.pauloeduardocosta.forum.service;

import com.pauloeduardocosta.forum.dto.NovoUsuarioDTO;
import com.pauloeduardocosta.forum.dto.UsuarioDTO;
import com.pauloeduardocosta.forum.model.Perfil;
import com.pauloeduardocosta.forum.model.SenhaTemporaria;
import com.pauloeduardocosta.forum.model.Usuario;
import com.pauloeduardocosta.forum.repository.IPerfilRepository;
import com.pauloeduardocosta.forum.repository.IUsuarioRepository;
import com.pauloeduardocosta.forum.service.exception.ContaNaoExisteException;
import com.pauloeduardocosta.forum.service.exception.EmailJaCadastradoException;
import com.pauloeduardocosta.forum.service.exception.ObjetoNaoEncotradoException;
import com.pauloeduardocosta.forum.service.exception.UsernameJaUtilizadoException;
import com.pauloeduardocosta.forum.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private IPerfilRepository perfilRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${forum.email.temporario.validade.validade.minutos}")
    private Long validadeEmMinutos;

    private static final Long ID_PERFIL_USUARIO = 1L;

    @Transactional
    public UsuarioDTO salvarUsuario(NovoUsuarioDTO novoUsuarioDTO) {
        Usuario usuario = new Usuario(novoUsuarioDTO.getNome(), novoUsuarioDTO.getUsername(), novoUsuarioDTO.getEmail());
        Optional<Perfil> perfil = perfilRepository.findById(ID_PERFIL_USUARIO);
        usuario.setSenha(bCryptPasswordEncoder.encode(novoUsuarioDTO.getSenha()));
        usuario.getPerfis().add(perfil.get());
        usuarioRepository.save(usuario);
        emailService.enviarEmailVerificacao(usuario);

        return new UsuarioDTO(usuario);
    }

    @Transactional
    public void verificarConta(String uuid) {
        Usuario usuario = verificarUUID(uuid);
        if(usuario.getVerificacaoEmail().getVerificado() == false) {
            usuario.getVerificacaoEmail().setVerificado(true);
            usuario.getVerificacaoEmail().setDataVerificacao(LocalDateTime.now());
        }
    }

    @Transactional
    public void gerarSenhaTemporaria(String email) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        if(usuario.isEmpty()){
            throw new ObjetoNaoEncotradoException("Usuario não encontrado.");
        }
        String senha = gerarSenhaTemporaria();
        SenhaTemporaria senhaTemporaria = new SenhaTemporaria(bCryptPasswordEncoder.encode(senha), validadeEmMinutos);
        usuario.get().setSenhaTemporaria(senhaTemporaria);

        emailService.enviarEmailNovaSenha(usuario.get(), senha);
    }

    @Transactional
    public void cancelarSenhaTemporaria(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if(usuario.isEmpty()){
            throw new ObjetoNaoEncotradoException("Usuario não encontrado.");
        }
        usuario.get().setSenhaTemporaria(null);
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

    private Usuario verificarUUID(String uuid) {
        Optional<Usuario> usuario = usuarioRepository.findByVerificacaoEmailUuid(uuid);
        return usuario.orElseThrow(() -> new ContaNaoExisteException("Essa conta não exite."));
    }

    private String gerarSenhaTemporaria() {
        String alfa = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numero = "0123456789";
        String specialChar = "!@#$%&*-+";
        StringBuilder sb = new StringBuilder("");

        for (int i = 0; i < 3; i++) {
            sb.append(StringUtils.random(1, alfa));
            sb.append(StringUtils.random(1, alfa).toLowerCase());
            sb.append(StringUtils.random(1, numero));
            sb.append(StringUtils.random(1, specialChar));
        }
        return sb.toString();
    }
}

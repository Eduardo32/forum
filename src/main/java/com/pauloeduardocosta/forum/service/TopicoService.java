package com.pauloeduardocosta.forum.service;

import com.pauloeduardocosta.forum.dto.AtualizarTopicoDTO;
import com.pauloeduardocosta.forum.dto.NovoTopicoDTO;
import com.pauloeduardocosta.forum.dto.TopicoCompletoDTO;
import com.pauloeduardocosta.forum.dto.TopicoDTO;
import com.pauloeduardocosta.forum.model.Resposta;
import com.pauloeduardocosta.forum.model.Topico;
import com.pauloeduardocosta.forum.model.Usuario;
import com.pauloeduardocosta.forum.model.enums.EStatusTopico;
import com.pauloeduardocosta.forum.repository.IRespostaRepository;
import com.pauloeduardocosta.forum.repository.ITopicoRepository;
import com.pauloeduardocosta.forum.repository.IUsuarioRepository;
import com.pauloeduardocosta.forum.service.exception.ObjetoNaoEncotradoException;
import com.pauloeduardocosta.forum.service.exception.UsuarioNaoEAutorException;
import com.pauloeduardocosta.forum.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TopicoService {

    @Autowired
    private ITopicoRepository topicoRepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private IRespostaRepository respostaRepository;

    @Autowired
    private TagService tagService;

    public Page<TopicoDTO> buscarTodos(Pageable paginacao) {
        Page<Topico> topicos = topicoRepository.findAll(paginacao);
        return TopicoDTO.converter(topicos);
    }

    public Page<TopicoDTO> buscarPorCurso(String mensagem, Pageable paginacao) {
        Page<Topico> topicos = topicoRepository.findByMensagemContainingIgnoreCase(mensagem, paginacao);
        return TopicoDTO.converter(topicos);
    }

    public TopicoCompletoDTO buscarTopicoPorId(Long topicoId) {
        return new TopicoCompletoDTO(buscarTopico(topicoId));
    }

    @Transactional
    public TopicoCompletoDTO criarTopico(NovoTopicoDTO novoTopicoDTO) {
        Usuario usuario = buscarUsuarioLogado();
        List<String> tagsSalvas = tagService.verificarTagNovas(novoTopicoDTO.getTags());
        String tags = StringUtils.converterParaStringSeparadaPorVirgula(tagsSalvas);
        Topico topico = new Topico(novoTopicoDTO.getTitulo(), novoTopicoDTO.getMensagem(), usuario);
        topico.setTags(tags);
        topicoRepository.save(topico);
        return new TopicoCompletoDTO(topico);
    }

    @Transactional
    public TopicoCompletoDTO marcarTopicoComoSolucionado(Long topicoId, Long respostaId) {
        Topico topico = buscarTopico(topicoId);
        validarAutor(topico.getAutor().getId());

        topico.getRespostas().forEach(resposta -> resposta.setSolucao(false));
        Optional<Resposta> resposta = topico.getRespostas().stream().filter(r -> r.getId() == respostaId).findFirst();

        if(resposta.isEmpty()) {
            throw new ObjetoNaoEncotradoException("Resposta com id " + respostaId + " n??o encontrado.");
        }

        resposta.get().setSolucao(true);
        topico.setStatus(EStatusTopico.SOLUCIONADO);

        return new TopicoCompletoDTO(topico);
    }

    @Transactional
    public TopicoCompletoDTO marcarTopicoComoNaoSolucionado(Long topicoId) {
        Topico topico = buscarTopico(topicoId);
        validarAutor(topico.getAutor().getId());

        topico.getRespostas().forEach(resposta -> resposta.setSolucao(false));
        topico.setStatus(EStatusTopico.NAO_SOLUCIONADO);

        return new TopicoCompletoDTO(topico);
    }

    @Transactional
    public TopicoCompletoDTO atualizarTopico(Long id, AtualizarTopicoDTO atualizarTopicoDTO) {
        Topico topico = buscarTopico(id);
        validarAutor(topico.getAutor().getId());

        if(atualizarTopicoDTO.getTitulo() != null && !atualizarTopicoDTO.getTitulo().isBlank()) {
            topico.setTitulo(atualizarTopicoDTO.getTitulo());
        }
        if(atualizarTopicoDTO.getMensagem() != null && !atualizarTopicoDTO.getMensagem().isBlank()) {
            topico.setMensagem(atualizarTopicoDTO.getMensagem());
        }

        return new TopicoCompletoDTO(topico);
    }

    @Transactional
    public TopicoCompletoDTO fecharTopico(Long topicoId) {
        Topico topico = buscarTopico(topicoId);
        validarAutor(topico.getAutor().getId());
        topico.setStatus(EStatusTopico.FECHADO);
        return new TopicoCompletoDTO(topico);
    }

    @Transactional
    public TopicoCompletoDTO reabrirTopico(Long topicoId) {
        Topico topico = buscarTopico(topicoId);
        validarAutor(topico.getAutor().getId());
        topico.setStatus(topico.getRespostas().size() == 0 ? EStatusTopico.NAO_RESPONDIDO : EStatusTopico.NAO_SOLUCIONADO);
        return new TopicoCompletoDTO(topico);
    }

    @Transactional
    public void excluirTopico(Long topicoId) {
        buscarTopico(topicoId);
        topicoRepository.deleteById(topicoId);
    }

    private Topico buscarTopico(Long topicoId) {
        Optional<Topico> topico = topicoRepository.findById(topicoId);
        return topico.orElseThrow(() -> new ObjetoNaoEncotradoException("Topico com id: " + topicoId + " n??o encontrado."));
    }

    private void validarAutor(Long autorId) {
        Usuario usuario = buscarUsuarioLogado();
        if(usuario.getId() != autorId) {
            throw new UsuarioNaoEAutorException("Esse usuario n??o ?? o autor desse topico.");
        }
    }

    private Usuario buscarUsuarioLogado() {
        Optional<Usuario> usuarioLogado = (Optional<Usuario>) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return usuarioLogado.get();
    }
}

package com.pauloeduardocosta.forum.service;

import com.pauloeduardocosta.forum.dto.AtualizarRespostaDTO;
import com.pauloeduardocosta.forum.dto.NovaRespostaDTO;
import com.pauloeduardocosta.forum.dto.RespostaDTO;
import com.pauloeduardocosta.forum.dto.TopicoCompletoDTO;
import com.pauloeduardocosta.forum.model.Resposta;
import com.pauloeduardocosta.forum.model.Topico;
import com.pauloeduardocosta.forum.model.Usuario;
import com.pauloeduardocosta.forum.model.enums.EStatusTopico;
import com.pauloeduardocosta.forum.repository.IRespostaRepository;
import com.pauloeduardocosta.forum.repository.ITopicoRepository;
import com.pauloeduardocosta.forum.repository.IUsuarioRepository;
import com.pauloeduardocosta.forum.service.exception.ObjetoNaoEncotradoException;
import com.pauloeduardocosta.forum.service.exception.TopicoFechadoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RespostaService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private ITopicoRepository topicoRepository;

    @Autowired
    private IRespostaRepository respostaRepository;

    @Transactional
    public RespostaDTO criarResposta(NovaRespostaDTO novaRespostaDTO) {
        //TODO: devera pegar o usuario logado
        Optional<Usuario> autor = usuarioRepository.findById(novaRespostaDTO.getAutorId());
        Optional<Topico> topicoEncontrado = topicoRepository.findById(novaRespostaDTO.getTopicoId());
        Topico topico = topicoEncontrado.orElseThrow(() -> new ObjetoNaoEncotradoException("Topico com id: " + novaRespostaDTO.getTopicoId() + " não encontrado."));

        if(topico.getStatus() == EStatusTopico.FECHADO) {
            throw new TopicoFechadoException("O topico id " + topico.getId() + " já está fechado e não pode receber mais respostas.");
        }
        if(topico.getStatus() == EStatusTopico.NAO_RESPONDIDO) {
            topico.setStatus(EStatusTopico.NAO_SOLUCIONADO);
        }
        Resposta resposta = new Resposta(novaRespostaDTO.getMensagem(), topico, autor.get());
        topico.getRespostas().add(resposta);
        respostaRepository.save(resposta);

        return new RespostaDTO(resposta);
    }

    @Transactional
    public RespostaDTO atualizarTopico(Long respostaId, AtualizarRespostaDTO atualizarRespostaDTO) {
        Resposta resposta = buscarResposta(respostaId);

        if(atualizarRespostaDTO.getMensagem() != null && !atualizarRespostaDTO.getMensagem().isBlank()) {
            resposta.setMensagem(atualizarRespostaDTO.getMensagem());
        }

        return new RespostaDTO(resposta);
    }

    private Resposta buscarResposta(Long respostaId) {
        Optional<Resposta> resposta = respostaRepository.findById(respostaId);
        return resposta.orElseThrow(() -> new ObjetoNaoEncotradoException("Topico com id: " + respostaId + " não encontrado."));
    }
}
